package com.marko.weightlosstracker.repository

import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.domain.Stats
import com.marko.weightlosstracker.domain.WeightEntry
import com.marko.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.parseDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeWeightEntryRepositoryTest @Inject constructor() : WeightEntryRepository {

    private var entries = mutableListOf<WeightEntry>()

    init {
        entries = DataGenerator.listOfEntries.toMutableList()
    }

    override suspend fun getAllEntries(): Flow<DataState<List<WeightEntry>>> = flow {
        val sortedList = entries.sortedByDescending {
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
        entries.add(weightEntry)
    }

    override suspend fun deleteWeightEntry(weightEntry: WeightEntry): Flow<DataState<List<WeightEntry>>> =
        flow {
            entries.remove(weightEntry)
            val sortedList = entries.sortedByDescending {
                parseDate(it.date)
            }
            emit(DataState.Success(sortedList))
        }

    override suspend fun reverseDeletionOfWeightEntry(weightEntry: WeightEntry): Flow<DataState<List<WeightEntry>>> =
        flow {
            entries.add(weightEntry)
            val sortedList = entries.sortedByDescending {
                parseDate(it.date)
            }
            emit(DataState.Success(sortedList))
        }
}