package com.marko.weightlosstracker.model

import com.marko.weightlosstracker.util.roundUp

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
) {

    fun getCaloriesBurnedString(): String = caloriesBurned.roundUp().toString()
    fun getCheeseburgersBurnedString(): String = cheeseburgersBurned.roundUp().toString()
    fun getTotalLossString(): String = totalLoss.roundUp().toString()
    fun getRemainingString(): String = remaining.roundUp().toString()
    fun getCurrentWeightString(): String = currentWeight.roundUp().toString()
    fun getTargetWeightString(): String = targetWeight.roundUp().toString()
}
