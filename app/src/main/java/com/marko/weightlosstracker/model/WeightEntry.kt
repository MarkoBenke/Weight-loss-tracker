package com.marko.weightlosstracker.model

data class WeightEntry(
    var uuid: String,
    val currentWeight: Float,
    val waistSize: Int,
    val date: String,
    var description: String
)
