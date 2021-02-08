package com.example.weightlosstracker.domain

data class WeightEntry(
    var uuid: Int = 0,
    val currentWeight: Float,
    val waistSize: Int,
    val date: String,
    val description: String
)
