package com.marko.weightlosstracker.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.marko.weightlosstracker.util.Constants.DATA_STORE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultSettingsManager constructor(context: Context) : SettingsManager {

    private var dataStore: DataStore<Preferences> = context.createDataStore(DATA_STORE_NAME)

    override suspend fun saveStartDate(startDate: Long) {
        val dataStoreKey = longPreferencesKey(START_DATE_KEY)
        dataStore.edit {
            it[dataStoreKey] = startDate
        }
    }

    override suspend fun getStartDate(): Flow<Long> {
        val dataStoreKey = longPreferencesKey(START_DATE_KEY)
        return dataStore.data.map { preferences ->
            preferences[dataStoreKey] ?: 0
        }
    }

    companion object {
        const val START_DATE_KEY = "start_date"
    }
}