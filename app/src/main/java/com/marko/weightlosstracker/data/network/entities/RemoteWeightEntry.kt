package com.marko.weightlosstracker.data.network.entities

import androidx.annotation.Keep

@Keep data class RemoteWeightEntry(
    var uuid: String = "",
    var currentWeight: Float = 0f,
    var waistSize: Int = 0,
    val date: String = "",
    var description: String = ""
)