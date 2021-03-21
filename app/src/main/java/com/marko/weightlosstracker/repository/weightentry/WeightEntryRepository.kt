package com.marko.weightlosstracker.repository.weightentry

import com.marko.weightlosstracker.model.Stats
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow

interface WeightEntryRepository {

    suspend fun getLocalEntries(): Flow<DataState<List<WeightEntry>>>
    suspend fun syncEntriesData(): Flow<Unit>
    suspend fun getUserStats(): Flow<Stats>
    suspend fun insertWeight(weightEntry: WeightEntry): Flow<DataState<Unit>>
    suspend fun deleteWeightEntryFromList(weightEntry: WeightEntry): Flow<DataState<List<WeightEntry>>>
    suspend fun deleteWeightEntry(weightEntry: WeightEntry): Flow<DataState<Unit>>
    suspend fun updateWeightEntry(weightEntry: WeightEntry): Flow<DataState<Unit>>
}