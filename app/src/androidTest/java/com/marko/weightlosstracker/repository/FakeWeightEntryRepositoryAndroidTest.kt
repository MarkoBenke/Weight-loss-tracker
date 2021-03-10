package com.marko.weightlosstracker.repository

import com.marko.weightlosstracker.model.Stats
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.parseDate
import com.marko.weightlosstracker.utils.DataGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeWeightEntryRepositoryAndroidTest @Inject constructor() : WeightEntryRepository {

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

    override suspend fun getUserStats(): Flow<Stats> = flow {
        emit(DataGenerator.stats)
    }

    override suspend fun insertWeight(weightEntry: WeightEntry) {
        entries.add(weightEntry)
    }

    override suspend fun deleteWeightEntryFromList(weightEntry: WeightEntry): Flow<DataState<List<WeightEntry>>> =
        flow {
            entries.remove(weightEntry)
            val sortedList = entries.sortedByDescending {
                parseDate(it.date)
            }
            emit(DataState.Success(sortedList))
        }

    override suspend fun deleteWeightEntry(weightEntry: WeightEntry): Flow<Boolean> = flow {
        entries.remove(weightEntry)
        emit(true)
    }

    override suspend fun updateWeightEntry(weightEntry: WeightEntry): Flow<Boolean> = flow {
        entries.forEach { value ->
            if (value.uuid == weightEntry.uuid) {
                value.description = weightEntry.description
                value.waistSize = weightEntry.waistSize
                value.currentWeight = weightEntry.currentWeight
            }
        }
        emit(true)
    }
}