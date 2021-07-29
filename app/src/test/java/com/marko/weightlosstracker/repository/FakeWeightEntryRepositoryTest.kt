package com.marko.weightlosstracker.repository

import com.marko.weightlosstracker.model.Stats
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.parseDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWeightEntryRepositoryTest(private val shouldReturnError: Boolean = false) :
    WeightEntryRepository {

    private var entries = mutableListOf<WeightEntry>()

    init {
        entries = DataGenerator.listOfEntries.toMutableList()
    }

    override suspend fun getLocalEntries(): Flow<DataState<List<WeightEntry>>> = flow {
        val sortedList = entries.sortedByDescending {
            parseDate(it.date)
        }
        emit(DataState.Success(sortedList))
    }

    override suspend fun syncEntriesData(): Flow<Unit> = flow {
        emit(Unit)
    }

    override suspend fun getUserStats(): Flow<Stats> = flow {
        emit(DataGenerator.stats)
    }

    override suspend fun insertWeight(weightEntry: WeightEntry): Flow<DataState<Unit>> = flow {
        if (shouldReturnError) {
            emit(DataState.Error())
            return@flow
        }
        entries.add(weightEntry)
        emit(DataState.Success(Unit))
    }

    override suspend fun deleteWeightEntryFromList(weightEntry: WeightEntry): Flow<DataState<List<WeightEntry>>> =
        flow {
            entries.remove(weightEntry)
            val sortedList = entries.sortedByDescending {
                parseDate(it.date)
            }
            emit(DataState.Success(sortedList))
        }

    override suspend fun deleteWeightEntry(weightEntry: WeightEntry): Flow<DataState<Unit>> = flow {
        if (shouldReturnError) {
            emit(DataState.Error())
            return@flow
        }
        entries.remove(weightEntry)
        emit(DataState.Success(Unit))
    }

    override suspend fun updateWeightEntry(weightEntry: WeightEntry): Flow<DataState<Unit>> = flow {
        if (shouldReturnError) {
            emit(DataState.Error())
            return@flow
        }
        entries.forEach { value ->
            if (value.uuid == weightEntry.uuid) {
                value.description = weightEntry.description
                value.waistSize = weightEntry.waistSize
                value.currentWeight = weightEntry.currentWeight
            }
        }
        emit(DataState.Success(Unit))
    }
}