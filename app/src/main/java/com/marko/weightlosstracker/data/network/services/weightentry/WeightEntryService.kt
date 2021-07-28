package com.marko.weightlosstracker.data.network.services.weightentry

import com.marko.weightlosstracker.data.network.entities.WeightEntryDto

interface WeightEntryService {
    suspend fun getAllEntries(): List<WeightEntryDto>?
    suspend fun insertWeightEntry(weightEntry: WeightEntryDto): Boolean
    suspend fun updateWeightEntry(weightEntry: WeightEntryDto): Boolean
    suspend fun deleteWeightEntry(id: String): Boolean
}