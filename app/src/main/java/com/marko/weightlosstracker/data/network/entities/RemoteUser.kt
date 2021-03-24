package com.marko.weightlosstracker.data.network.entities

import androidx.annotation.Keep

@Keep data class RemoteUser(
    var id: String = "",
    var username: String = "",
    var startWeight: Float = 0f,
    var currentWeight: Float = 0f,
    var targetWeight: Float = 0f,
    var startWaistSize: Int = 0,
    var startBmi: Float = 0f,
    var height: Float = 0f,
    var startDate: String = "",
    var age: Int = 0,
    var gender: String = "",
    var goalName: String = ""
)