package com.marko.weightlosstracker.repository.weightentry

import com.marko.weightlosstracker.data.local.dao.UserDao
import com.marko.weightlosstracker.data.local.dao.WeightEntryDao
import com.marko.weightlosstracker.data.local.entities.UserEntity
import com.marko.weightlosstracker.data.network.services.user.UserService
import com.marko.weightlosstracker.data.network.services.weightentry.WeightEntryService
import com.marko.weightlosstracker.model.Stats
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.model.mappers.UserMapper
import com.marko.weightlosstracker.model.mappers.WeightEntryMapper
import com.marko.weightlosstracker.util.Constants
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.calculateBmi
import com.marko.weightlosstracker.util.parseDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultWeightEntryRepository(
    private val weightEntryDao: WeightEntryDao,
    private val weightEntryService: WeightEntryService,
    private val userDao: UserDao,
    private val userService: UserService,
    private val weightEntryMapper: WeightEntryMapper,
    private val userMapper: UserMapper
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
                    weightEntryDao.insertWeightEntry(weightEntryMapper.dtoToEntity(it))
                }
            }
        }
        emit(Unit)
    }

    override suspend fun insertWeight(weightEntry: WeightEntry): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)

        val entryResult =
            weightEntryService.insertWeightEntry(weightEntryMapper.mapToDto(weightEntry))
        if (entryResult) {
            weightEntryDao.insertWeightEntry(weightEntryMapper.mapToEntity(weightEntry))
            emit(DataState.Success(Unit))
        } else emit(DataState.Error())

        val user = userDao.getUser()
        //if user modifies his initial weight entry, modify users starting values as well
        if (user?.startDate == weightEntry.date) {
            modifyUserAndEntry(user, weightEntry)
            val result = userService.updateUserWeightData(userMapper.entityToDto(user))
            if (result) {
                userDao.updateUser(user)
                emit(DataState.Success(Unit))
            } else emit(DataState.Error())
        }
    }

    override suspend fun updateWeightEntry(weightEntry: WeightEntry): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)

        val entryResult =
            weightEntryService.updateWeightEntry(weightEntryMapper.mapToDto(weightEntry))
        if (entryResult) {
            weightEntryDao.updateWeightEntry(weightEntryMapper.mapToEntity(weightEntry))
            emit(DataState.Success(Unit))
        } else emit(DataState.Error())

        if (weightEntry.isInitialEntry) {
            //if user modifies his initial weight entry, modify users starting values as well
            val user = userDao.getUser()
            user?.let {
                modifyUserAndEntry(user, weightEntry)
                val userResult =
                    userService.updateUserWeightData(userMapper.entityToDto(user))
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

    private fun modifyUserAndEntry(user: UserEntity?, weightEntry: WeightEntry) {
        user?.apply {
            startWeight = weightEntry.currentWeight
            startBmi = calculateBmi(weightEntry.currentWeight, height)
            if (weightEntry.waistSize != 0) {
                startWaistSize = weightEntry.waistSize
            }
        }
    }
}