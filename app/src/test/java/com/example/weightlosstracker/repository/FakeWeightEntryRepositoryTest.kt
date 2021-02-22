package com.example.weightlosstracker.repository

import com.example.weightlosstracker.other.DataGenerator
import com.example.weightlosstracker.domain.Stats
import com.example.weightlosstracker.domain.WeightEntry
import com.example.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.parseDate
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