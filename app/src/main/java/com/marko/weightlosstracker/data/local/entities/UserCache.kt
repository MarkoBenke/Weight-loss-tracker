package com.marko.weightlosstracker.data.local.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
@Keep data class UserCache(
    @PrimaryKey
    val uuid: Int = 0,
    var username: String,
    var startWeight: Float,
    val currentWeight: Float,
    val targetWeight: Float,
    var startWaistSize: Int,
    var startBmi: Float,
    val height: Float,
    val startDate: String,
    val age: Int,
    val gender: String,
    val goalName: String
)
