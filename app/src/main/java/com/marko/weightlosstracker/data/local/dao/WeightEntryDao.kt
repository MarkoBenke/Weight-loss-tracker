package com.marko.weightlosstracker.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.marko.weightlosstracker.data.local.entities.WeightEntryEntity

@Dao
interface WeightEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeightEntry(weightEntry: WeightEntryEntity)

    @Update()
    suspend fun updateWeightEntry(weightEntry: WeightEntryEntity)

    @Delete
    suspend fun deleteWeightEntry(weightEntry: WeightEntryEntity)

    @Query("SELECT * FROM weight_entries_table")
    suspend fun getAllWeightEntries(): List<WeightEntryEntity>

    @Query("SELECT * FROM weight_entries_table")
    fun getWeightEntriesLiveData(): LiveData<List<WeightEntryEntity>>
}