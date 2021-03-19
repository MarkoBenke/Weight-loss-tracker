package com.marko.weightlosstracker.data.local

import kotlinx.coroutines.flow.Flow

interface SettingsManager {

    suspend fun saveStartDate(startDate: Long)
    suspend fun getStartDate(): Flow<Long>

    suspend fun saveUserId(userId: String?)
    suspend fun getUserId(): Flow<String>
}