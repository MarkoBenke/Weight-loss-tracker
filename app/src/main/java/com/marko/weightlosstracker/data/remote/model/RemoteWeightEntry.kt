package com.marko.weightlosstracker.data.remote.model

data class RemoteWeightEntry(
    var uuid: String,
    var currentWeight: Float,
    var waistSize: Int,
    val date: String,
    var description: String,
)