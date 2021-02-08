package com.example.weightlosstracker.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uuid: Int = 0,
    var startWeight: Float = 0f,
    var currentWeight: Float = 0f,
    var targetWeight: Float = 0f,
    var startWaistSize: Int = 0,
    var startBmi: Float = 0f,
    var height: Float = 0f,
    var startDate: String = "",
    var age: Int = 0,
    var gender: Gender = Gender.MALE,
    var goalName: String = ""
) : Parcelable