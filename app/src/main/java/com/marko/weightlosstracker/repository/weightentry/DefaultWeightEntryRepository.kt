package com.marko.weightlosstracker.repository.weightentry

import com.marko.weightlosstracker.data.local.dao.UserDao
import com.marko.weightlosstracker.data.local.dao.WeightEntryDao
import com.marko.weightlosstracker.data.local.mappers.WeightEntryMapper
import com.marko.weightlosstracker.data.local.entities.UserCache
import com.marko.weightlosstracker.data.network.services.UserService
import com.marko.weightlosstracker.data.network.services.WeightEntryService
import com.marko.weightlosstracker.data.util.UserTable
import com.marko.weightlosstracker.data.util.WeightEntryTable
import com.marko.weightlosstracker.model.Stats
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultWeightEntryRepository(
    private val weightEntryDao: WeightEntryDao,
    private val weightEntryService: WeightEntryService,
    private val userDao: UserDao,
    private val userService: UserService,
    private val weightEntryMapper: WeightEntryMapper
) : WeightEntryRepository {

    override suspend fun getLocalEntries(): Flow<DataState<List<WeightEntry>>> = flow {
        emit(DataState.Loading)

        val entries = weightEntryDao.getAllWeightEntries()
        val sortedList = entries.sortedByDescending {
            parseDate(it.date)
        }
        emit(DataState.Success(weightEntryMapper.mapFromEntityList(sortedList)))
    }

    override suspend fun syncEntriesData(): Flow<Unit> = flow {
        val entriesResult = weightEntryService.getAllEntries()
        entriesResult?.let {
            val localEntries = weightEntryDao.getAllWeightEntries()
            if (entriesResult.size > localEntries.size) {
                entriesResult.forEach {
                    weightEntryDao.insertWeightEntry(weightEntryMapper.remoteToLocal(it))
                }
            }
        }
        emit(Unit)
    }

    override suspend fun insertWeight(weightEntry: WeightEntry): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)

        val entryResult = weightEntryService.insertWeightEntry(weightEntryMapper.mapToRemoteEntity(weightEntry))
        if (entryResult) {
            weightEntryDao.insertWeightEntry(weightEntryMapper.mapToEntity(weightEntry))
            emit(DataState.Success(Unit))
        } else emit(DataState.Error())

        val user = userDao.getUser()
        //if user modifies his initial weight entry, modify users starting values as well
        if (user?.startDate == weightEntry.date) {
            modifyUserAndEntry(user, weightEntry)
            val userMap = getUserMap(user)
            val result = userService.updateUser(userMap)
            if (result) {
                userDao.updateUser(user)
                emit(DataState.Success(Unit))
            } else emit(DataState.Error())
        }
    }

    override suspend fun updateWeightEntry(weightEntry: WeightEntry): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)

        val entryResult = weightEntryService.updateWeightEntry(getWeightEntryMap(weightEntry))
        if (entryResult) {
            weightEntryDao.updateWeightEntry(weightEntryMapper.mapToEntity(weightEntry))
            emit(DataState.Success(Unit))
        } else emit(DataState.Error())

        if (weightEntry.isInitialEntry) {
            //if user modifies his initial weight entry, modify users starting values as well
            val user = userDao.getUser()
            user?.let {
                modifyUserAndEntry(user, weightEntry)
                val userMap = getUserMap(user)
                val userResult = userService.updateUser(userMap)
                if (userResult) {
                    userDao.updateUser(user)
                    emit(DataState.Success(Unit))
                } else emit(DataState.Error())
            } ?: emit(DataState.Error())
        }
    }

    override suspend fun deleteWeightEntryFromList(weightEntry: WeightEntry): Flow<DataState<List<WeightEntry>>> =
        flow {
            emit(DataState.Loading)
            val result = weightEntryService.deleteWeightEntry(weightEntry.uuid)
            if (result) {
                weightEntryDao.deleteWeightEntry(weightEntryMapper.mapToEntity(weightEntry))
                val entries = weightEntryDao.getAllWeightEntries()
                val sortedList = entries.sortedByDescending {
                    parseDate(it.date)
                }
                emit(DataState.Success(weightEntryMapper.mapFromEntityList(sortedList)))
            } else emit(DataState.Error())
        }

    override suspend fun deleteWeightEntry(weightEntry: WeightEntry): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        val result = weightEntryService.deleteWeightEntry(weightEntry.uuid)
        if (result) {
            weightEntryDao.deleteWeightEntry(weightEntryMapper.mapToEntity(weightEntry))
            emit(DataState.Success(Unit))
        } else emit(DataState.Error())
    }

    override suspend fun getUserStats(): Flow<Stats> = flow {
        val userData = userDao.getUser()
        val entries = weightEntryDao.getAllWeightEntries()
        if (entries.isNotEmpty()) {
            val sortedList = entries.sortedByDescending {
                parseDate(it.date)
            }
            val lastEntry = sortedList.first()
            val waistSizeLoss = if (lastEntry.waistSize == 0 || userData!!.startWaistSize == 0)
                0 else lastEntry.waistSize - userData.startWaistSize
            val totalLoss = userData!!.startWeight - lastEntry.currentWeight
            val caloriesBurned = totalLoss * Constants.CALORIES_IN_KG
            val stats = Stats(
                bmi = calculateBmi(lastEntry.currentWeight, userData.height),
                startBmi = calculateBmi(userData.startWeight, userData.height),
                startWeight = userData.startWeight,
                currentWeight = lastEntry.currentWeight,
                targetWeight = userData.targetWeight,
                startDate = userData.startDate,
                lastEntryDate = lastEntry.date,
                totalLoss = totalLoss,
                remaining = lastEntry.currentWeight - userData.targetWeight,
                currentWaistSize = lastEntry.waistSize,
                waistSizeLoss = waistSizeLoss,
                caloriesBurned = caloriesBurned,
                cheeseburgersBurned = caloriesBurned / Constants.CALORIES_IN_CHEESEBURGER
            )
            emit(stats)
        }
    }

    private fun getUserMap(user: UserCache?): HashMap<String, Any?> {
        return hashMapOf(
            UserTable.START_WAIST_SIZE to user?.startWaistSize,
            UserTable.START_BMI to user?.startBmi,
            UserTable.START_WEIGHT to user?.startWeight
        )
    }

    private fun getWeightEntryMap(weightEntry: WeightEntry): HashMap<String, Any> {
        return hashMapOf(
            WeightEntryTable.UUID to weightEntry.uuid,
            WeightEntryTable.CURRENT_WEIGHT to weightEntry.currentWeight,
            WeightEntryTable.WAIST_SIZE to weightEntry.waistSize,
            WeightEntryTable.DESCRIPTION to weightEntry.description
        )
    }

    private fun modifyUserAndEntry(user: UserCache?, weightEntry: WeightEntry) {
        user?.apply {
            startWeight = weightEntry.currentWeight
            startBmi = calculateBmi(weightEntry.currentWeight, height)
            if (weightEntry.waistSize != 0) {
                startWaistSize = weightEntry.waistSize
            }
        }
    }
}