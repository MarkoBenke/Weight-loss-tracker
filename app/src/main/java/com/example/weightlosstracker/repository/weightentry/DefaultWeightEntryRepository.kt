package com.example.weightlosstracker.repository.weightentry

import com.example.weightlosstracker.data.local.dao.UserDAO
import com.example.weightlosstracker.data.local.dao.WeightEntryDAO
import com.example.weightlosstracker.data.local.mappers.WeightEntryMapper
import com.example.weightlosstracker.domain.Stats
import com.example.weightlosstracker.domain.WeightEntry
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.calculateBmi
import com.example.weightlosstracker.util.parseDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultWeightEntryRepository constructor(
    private val weightEntryDAO: WeightEntryDAO,
    private val userDAO: UserDAO,
    private val mapper: WeightEntryMapper
) : WeightEntryRepository {

    override suspend fun getAllEntries(): Flow<DataState<List<WeightEntry>>> = flow {
        emit(DataState.Loading)

        val entries = weightEntryDAO.getAllWeightEntries()
        val sortedList = entries.sortedByDescending {
            parseDate(it.date)
        }
        emit(DataState.Success(mapper.mapFromEntityList(sortedList)))
    }

    override suspend fun getLastEntry(): Flow<DataState<WeightEntry>> = flow {
        emit(DataState.Loading)

        val entries = weightEntryDAO.getAllWeightEntries()
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
        val userData = userDAO.getUser()
        val entries = weightEntryDAO.getAllWeightEntries()
        if (entries.isNotEmpty()) {
            val sortedList = entries.sortedByDescending {
                parseDate(it.date)
            }
            val lastEntry = sortedList.first()
            val stats = Stats(
                bmi = calculateBmi(lastEntry.currentWeight, userData!!.height),
                startBmi = calculateBmi(userData.startWeight, userData.height),
                startWeight = userData.startWeight,
                currentWeight = lastEntry.currentWeight,
                targetWeight = userData.targetWeight,
                startDate = userData.startDate,
                lastEntryDate = lastEntry.date,
                totalLoss = userData.startWeight - lastEntry.currentWeight,
                remaining = lastEntry.currentWeight - userData.targetWeight
            )
            emit(stats)
        }
    }

    override suspend fun insertWeight(weightEntry: WeightEntry) {
        weightEntryDAO.insertWeightEntry(mapper.mapToEntity(weightEntry))
    }
}