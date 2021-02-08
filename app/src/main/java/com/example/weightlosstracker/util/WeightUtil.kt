package com.example.weightlosstracker.util

fun calculateBmi(weight: Float, height: Float): Float {
    return weight / (height * height) * 10000
}

fun getBmiType(bmi: Float): Int {
    return if (bmi < Constants.UNDERWEIGHT) {
        0
    } else if (bmi >= Constants.UNDERWEIGHT && bmi <= Constants.NORMAL_END) {
        1
    } else if (bmi >= Constants.NORMAL_END && bmi < Constants.OVERWEIGHT) {
        2
    } else 3
}

