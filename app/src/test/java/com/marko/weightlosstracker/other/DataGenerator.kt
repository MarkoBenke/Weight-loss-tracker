package com.marko.weightlosstracker.other

import com.marko.weightlosstracker.model.*
import com.marko.weightlosstracker.util.getCurrentDate

object DataGenerator {

    val quote = Quote(
        1, "2Pac", "Motivational", "Only God can judge me."
    )

    val user = User(
        startWeight = 95f, currentWeight = 85f, targetWeight = 75f, startWaistSize = 102,
        startBmi = 30.2f, height = 173f, startDate = "01.01.2021", age = 30, gender = Gender.MALE,
        goalName = "Journey to 25kg", username = "Mare"
    )

    val stats = Stats(
        15f, 15f, 95f, 85f,
        75f, "30.01.1991.", "30.01.2002.",
        20f, 15f, 12, 2, 120f, 2f
    )

    val weightEntry = WeightEntry(
        uuid = "3",
        currentWeight = 85f, waistSize = 95, date = getCurrentDate(), description = "Description"
    )

    val listOfEntries = arrayListOf<WeightEntry>().apply {
        add(WeightEntry("2", 90f, 100, "30.01.2020", ""))
        add(WeightEntry("1", 95f, 105, "30.01.2019", ""))
        add(weightEntry)
    }
}