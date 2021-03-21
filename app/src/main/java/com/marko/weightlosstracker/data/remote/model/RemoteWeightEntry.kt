package com.marko.weightlosstracker.data.remote.model

data class RemoteWeightEntry(
    var uuid: String = "",
    var currentWeight: Float = 0f,
    var waistSize: Int = 0,
    val date: String = "",
    var description: String = ""
)