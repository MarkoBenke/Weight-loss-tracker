package com.example.weightlosstracker.data.local

import kotlinx.coroutines.flow.Flow

interface SettingsManager {

    suspend fun saveStartDate(startDate: Long)
    suspend fun getStartDate(): Flow<Long>
}