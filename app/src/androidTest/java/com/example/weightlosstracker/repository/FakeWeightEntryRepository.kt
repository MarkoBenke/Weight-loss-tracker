package com.example.weightlosstracker.repository

import com.example.weightlosstracker.domain.Stats
import com.example.weightlosstracker.domain.WeightEntry
import com.example.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.example.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeightEntryRepository : WeightEntryRepository {

    override suspend fun getAllEntries(): Flow<DataState<List<WeightEntry>>> = flow {
        emit(DataState.Success(arrayListOf()))
    }

    override suspend fun getLastEntry(): Flow<DataState<WeightEntry>> = flow {
        emit(DataState.Success(WeightEntry(
            currentWeight = 95f,
            date = "25.05.2005.",
            description = "desc"
        )))
    }

    override suspend fun getUserStats(): Flow<Stats> = flow {
        emit(Stats(15f, 15f, 95f, 85f, 75f, "30.01.1991.", "30.01.2002.", 20f, 15f))
    }


    override suspend fun insertWeight(weightEntry: WeightEntry) {

    }
}