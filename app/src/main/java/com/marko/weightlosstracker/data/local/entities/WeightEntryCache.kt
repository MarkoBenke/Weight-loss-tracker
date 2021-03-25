package com.marko.weightlosstracker.data.local.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_entries_table")
@Keep class WeightEntryCache(
    @PrimaryKey(autoGenerate = false)
    var uuid: String,
    var currentWeight: Float,
    val waistSize: Int,
    val date: String,
    val description: String = ""
)