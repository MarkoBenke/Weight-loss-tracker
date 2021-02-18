package com.example.weightlosstracker.repository

import com.example.weightlosstracker.domain.Stats
import com.example.weightlosstracker.domain.WeightEntry
import com.example.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.parseDate
import com.example.weightlosstracker.utils.DataGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeWeightEntryRepositoryAndroidTest @Inject constructor() : WeightEntryRepository {

    override suspend fun getAllEntries(): Flow<DataState<List<WeightEntry>>> = flow {
        val sortedList = DataGenerator.listOfEntries.sortedByDescending {
            parseDate(it.date)
        }
        emit(DataState.Success(sortedList))
    }

    override suspend fun getLastEntry(): Flow<DataState<WeightEntry>> = flow {
        emit(DataState.Success(DataGenerator.weightEntry))
    }

    override suspend fun getUserStats(): Flow<Stats> = flow {
        emit(DataGenerator.stats)
    }

    override suspend fun insertWeight(weightEntry: WeightEntry) {

    }
}