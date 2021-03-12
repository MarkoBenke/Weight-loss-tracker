package com.marko.weightlosstracker.data.remote.model

data class RemoteUser(
    val id: String = "",
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