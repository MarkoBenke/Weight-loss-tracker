package com.marko.weightlosstracker.data.local.settings

import kotlinx.coroutines.flow.Flow

interface SettingsManager {

    suspend fun saveStartDate(startDate: Long)
    suspend fun getStartDate(): Flow<Long>
}