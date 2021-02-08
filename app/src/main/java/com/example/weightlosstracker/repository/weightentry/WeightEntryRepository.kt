package com.example.weightlosstracker.repository.weightentry

import com.example.weightlosstracker.domain.Stats
import com.example.weightlosstracker.domain.WeightEntry
import com.example.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow

interface WeightEntryRepository {

    suspend fun getAllEntries(): Flow<DataState<List<WeightEntry>>>
    suspend fun getLastEntry(): Flow<DataState<WeightEntry>>
    suspend fun getUserStats(): Flow<Stats>
    suspend fun insertWeight(weightEntry: WeightEntry)
}