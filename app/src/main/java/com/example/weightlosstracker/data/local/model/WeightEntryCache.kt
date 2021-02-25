package com.example.weightlosstracker.data.local.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_entries_table")
@Keep class WeightEntryCache(
    @PrimaryKey(autoGenerate = false)
    var uuid: String,
    val currentWeight: Float,
    val waistSize: Int,
    val date: String,
    val description: String = ""
)
