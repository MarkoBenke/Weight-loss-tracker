package com.marko.weightlosstracker.repository.weightentry

import com.marko.weightlosstracker.data.local.dao.UserDao
import com.marko.weightlosstracker.data.local.dao.WeightEntryDao
import com.marko.weightlosstracker.data.local.mappers.WeightEntryMapper
import com.marko.weightlosstracker.model.Stats
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.util.Constants
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.calculateBmi
import com.marko.weightlosstracker.util.parseDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultWeightEntryRepository constructor(
    private val weightEntryDao: WeightEntryDao,
    private val userDao: UserDao,
    private val mapper: WeightEntryMapper
) : WeightEntryRepository {

    override suspend fun getAllEntries(): Flow<DataState<List<WeightEntry>>> = flow {
        emit(DataState.Loading)

        val entries = weightEntryDao.getAllWeightEntries()
        val sortedList = entries.sortedByDescending {
            parseDate(it.date)
        }
        emit(DataState.Success(mapper.mapFromEntityList(sortedList)))
    }

    override suspend fun getLastEntry(): Flow<DataState<WeightEntry>> = flow {
        emit(DataState.Loading)

        val entries = weightEntryDao.getAllWeightEntries()
        if (entries.isEmpty()) {
            emit(DataState.Error("No data found"))
        } else {
            val sortedList = entries.sortedByDescending {
                parseDate(it.date)
            }
            emit(DataState.Success(mapper.mapFromEntity(sortedList.first())))
        }
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

    override suspend fun insertWeight(weightEntry: WeightEntry) {
        val user = userDao.getUser()
        //if user modifies his first entry, change user object as well
        if (user?.startDate == weightEntry.date) {
            user.apply {
                startWeight = weightEntry.currentWeight
                if (weightEntry.waistSize != 0) {
                    startWaistSize = weightEntry.waistSize
                }
                startBmi = calculateBmi(weightEntry.currentWeight, height)
            }
            weightEntry.description = user.goalName
            userDao.updateUser(user)
        }
        weightEntryDao.insertWeightEntry(mapper.mapToEntity(weightEntry))
    }

    override suspend fun deleteWeightEntry(weightEntry: WeightEntry): Flow<DataState<List<WeightEntry>>> =
        flow {
            emit(DataState.Loading)
            weightEntryDao.deleteWeightEntry(mapper.mapToEntity(weightEntry))

            val entries = weightEntryDao.getAllWeightEntries()
            val sortedList = entries.sortedByDescending {
                parseDate(it.date)
            }
            emit(DataState.Success(mapper.mapFromEntityList(sortedList)))
        }

    override suspend fun reverseDeletionOfWeightEntry(weightEntry: WeightEntry): Flow<DataState<List<WeightEntry>>> =
        flow {
            emit(DataState.Loading)
            weightEntryDao.insertWeightEntry(mapper.mapToEntity(weightEntry))

            val entries = weightEntryDao.getAllWeightEntries()
            val sortedList = entries.sortedByDescending {
                parseDate(it.date)
            }
            emit(DataState.Success(mapper.mapFromEntityList(sortedList)))
        }
}