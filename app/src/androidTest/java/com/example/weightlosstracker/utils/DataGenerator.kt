package com.example.weightlosstracker.utils

import com.example.weightlosstracker.data.local.model.QuoteCache
import com.example.weightlosstracker.data.local.model.UserCache
import com.example.weightlosstracker.data.local.model.WeightEntryCache

object DataGenerator {

    val quote = QuoteCache(
        1, "2Pac", "Motivational", "Only God can judge me."
    )

    val user = UserCache(
        startWeight = 95f, currentWeight = 85f, targetWeight = 75f, startWaistSize = 102,
        startBmi = 30.2f, height = 173f, startDate = "30.01.2021", age = 30, gender = "MALE",
        goalName = "Journey to 75kg"
    )

    val weightEntry = WeightEntryCache(
        uuid = 1,
        currentWeight = 85f, waistSize = 95, date = "30.01.2021", description = "Description"
    )
}