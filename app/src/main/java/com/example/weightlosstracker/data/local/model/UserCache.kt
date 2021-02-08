package com.example.weightlosstracker.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserCache(
    @PrimaryKey
    val uuid: Int = 0,
    val startWeight: Float,
    val currentWeight: Float,
    val targetWeight: Float,
    val startWaistSize: Int,
    val startBmi: Float,
    val height: Float,
    val startDate: String,
    val age: Int,
    val gender: String,
    val goalName: String
)
