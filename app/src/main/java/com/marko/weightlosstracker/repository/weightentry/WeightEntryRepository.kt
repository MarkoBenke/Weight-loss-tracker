package com.marko.weightlosstracker.repository.weightentry

import com.marko.weightlosstracker.domain.Stats
import com.marko.weightlosstracker.domain.WeightEntry
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow

interface WeightEntryRepository {

    suspend fun getAllEntries(): Flow<DataState<List<WeightEntry>>>
    suspend fun getLastEntry(): Flow<DataState<WeightEntry>>
    suspend fun getUserStats(): Flow<Stats>
    suspend fun insertWeight(weightEntry: WeightEntry)
    suspend fun deleteWeightEntry(weightEntry: WeightEntry): Flow<DataState<List<WeightEntry>>>
    suspend fun reverseDeletionOfWeightEntry(weightEntry: WeightEntry): Flow<DataState<List<WeightEntry>>>
}