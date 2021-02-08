package com.example.weightlosstracker.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weightlosstracker.data.local.model.WeightEntryCache

@Dao
interface WeightEntryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeightEntry(weightEntry: WeightEntryCache)

    @Query("SELECT * FROM weight_entries_table")
    suspend fun getAllWeightEntries(): List<WeightEntryCache>

    @Query("SELECT * FROM weight_entries_table")
    fun getWeightEntriesLiveData(): LiveData<List<WeightEntryCache>>
}