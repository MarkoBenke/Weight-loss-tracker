package com.marko.weightlosstracker.domain

data class Stats(
    val bmi: Float,
    val startBmi: Float,
    val startWeight: Float,
    val currentWeight: Float,
    val targetWeight: Float,
    val startDate: String,
    val lastEntryDate: String,
    val totalLoss: Float,
    val remaining: Float,
    val currentWaistSize: Int,
    val waistSizeLoss: Int,
    val caloriesBurned: Float,
    val cheeseburgersBurned: Float
//    val gramsPerDay: String,
//    val daysPast: Int,
)