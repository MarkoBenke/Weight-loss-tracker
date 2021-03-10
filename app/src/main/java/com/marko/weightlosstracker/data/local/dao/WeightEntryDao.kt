package com.marko.weightlosstracker.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.marko.weightlosstracker.data.local.model.WeightEntryCache

@Dao
interface WeightEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeightEntry(weightEntry: WeightEntryCache)

    @Update()
    suspend fun updateWeightEntry(weightEntry: WeightEntryCache)

    @Delete
    suspend fun deleteWeightEntry(weightEntry: WeightEntryCache)

    @Query("SELECT * FROM weight_entries_table")
    suspend fun getAllWeightEntries(): List<WeightEntryCache>

    @Query("SELECT * FROM weight_entries_table")
    fun getWeightEntriesLiveData(): LiveData<List<WeightEntryCache>>
}