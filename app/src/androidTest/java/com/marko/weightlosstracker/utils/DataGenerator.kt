package com.marko.weightlosstracker.utils

import com.marko.weightlosstracker.data.local.entities.QuoteEntity
import com.marko.weightlosstracker.data.local.entities.UserEntity
import com.marko.weightlosstracker.data.local.entities.WeightEntryEntity
import com.marko.weightlosstracker.model.*

object DataGenerator {

    val largeName = "Marko Benke Marko Benke"
    val largeNumber = "123456"

    val quoteCache = QuoteEntity(
        1, "2Pac", "Motivational", "Only God can judge me."
    )

    val quote = Quote(
        1, "2Pac", "Motivational", "Only God can judge me."
    )

    val userCache = UserEntity(
        username = "Marko",
        startWeight = 95f, currentWeight = 85f, targetWeight = 75f, startWaistSize = 102,
        startBmi = 30.2f, height = 173f, startDate = "30.01.2021", age = 30, gender = "MALE",
        goalName = "Journey to 75kg"
    )

    val user = User(
        username = "Marko",
        startWeight = 95f, currentWeight = 85f, targetWeight = 75f, startWaistSize = 102,
        startBmi = 30.2f, height = 173f, startDate = "01.01.2021", age = 30, gender = Gender.MALE,
        goalName = "Journey to 25kg"
    )

    val stats = Stats(15f, 15f, 95f, 85f,
        75f, "30.01.1991.", "30.01.2002.",
        20f, 15f, 12,2, 120f, 2f)

    val weightEntryCache = WeightEntryEntity(
        uuid = "1",
        currentWeight = 85f, waistSize = 95, date = "30.01.2021", description = "Description"
    )

    val weightEntry = WeightEntry(
        uuid = "3",
        currentWeight = 92f, waistSize = 95, date = "30.01.2021", description = "Description"
    )

    val listOfEntries = arrayListOf<WeightEntry>().apply {
        add(WeightEntry("2", 90f, 0, "30.01.2020", "desc"))
        add(WeightEntry("1", 95f, 105, "30.01.2019", ""))
        add(weightEntry)
    }
}