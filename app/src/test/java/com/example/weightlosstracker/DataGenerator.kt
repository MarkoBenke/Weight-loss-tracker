package com.example.weightlosstracker

import com.example.weightlosstracker.data.local.model.QuoteCache
import com.example.weightlosstracker.data.local.model.UserCache
import com.example.weightlosstracker.data.local.model.WeightEntryCache
import com.example.weightlosstracker.domain.Gender
import com.example.weightlosstracker.domain.User
import com.example.weightlosstracker.domain.WeightEntry

object DataGenerator {

    val quote = QuoteCache(
        1, "2Pac", "Motivational", "Only God can judge me."
    )

    val userCache = UserCache(
        startWeight = 95f, currentWeight = 85f, targetWeight = 75f, startWaistSize = 102,
        startBmi = 30.2f, height = 173f, startDate = "30.01.2021", age = 30, gender = "MALE",
        goalName = "Journey to 75kg"
    )

    val user = User(
        startWeight = 95f, currentWeight = 85f, targetWeight = 75f, startWaistSize = 102,
        startBmi = 30.2f, height = 173f, startDate = "30.01.2021", age = 30, gender = Gender.MALE,
        goalName = "Journey to 75kg"
    )

    val weightEntryCache = WeightEntryCache(
        uuid = 1,
        currentWeight = 85f, waistSize = 95, date = "30.01.2021", description = "Description"
    )

    val weightEntry = WeightEntry(
        uuid = 1,
        currentWeight = 85f, waistSize = 95, date = "30.01.2021", description = "Description"
    )
}