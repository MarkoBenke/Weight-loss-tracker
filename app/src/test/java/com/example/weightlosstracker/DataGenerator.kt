package com.example.weightlosstracker

import com.example.weightlosstracker.data.local.model.QuoteCache
import com.example.weightlosstracker.data.local.model.UserCache
import com.example.weightlosstracker.data.local.model.WeightEntryCache
import com.example.weightlosstracker.domain.*
import com.example.weightlosstracker.util.getCurrentDate

object DataGenerator {

    val quoteCache = QuoteCache(
        1, "2Pac", "Motivational", "Only God can judge me."
    )

    val quote = Quote(
        1, "2Pac", "Motivational", "Only God can judge me."
    )

    val userCache = UserCache(
        startWeight = 95f, currentWeight = 85f, targetWeight = 75f, startWaistSize = 102,
        startBmi = 30.2f, height = 173f, startDate = "30.01.2021", age = 30, gender = "MALE",
        goalName = "Journey to 75kg"
    )

    val user = User(
        startWeight = 95f, currentWeight = 85f, targetWeight = 75f, startWaistSize = 102,
        startBmi = 30.2f, height = 173f, startDate = "01.01.2021", age = 30, gender = Gender.MALE,
        goalName = "Journey to 25kg"
    )

    val stats = Stats(15f, 15f, 95f, 85f,
        75f, "30.01.1991.", "30.01.2002.",
        20f, 15f)

    val weightEntryCache = WeightEntryCache(
        uuid = 1,
        currentWeight = 85f, waistSize = 95, date = "30.01.2021", description = "Description"
    )

    val weightEntry = WeightEntry(
        uuid = 1,
        currentWeight = 85f, waistSize = 95, date = getCurrentDate(), description = "Description"
    )

    val listOfEntries = arrayListOf<WeightEntry>().apply {
        add(WeightEntry(2, 90f, 100, "30.01.2020", ""))
        add(WeightEntry(3, 95f, 105, "30.01.2019", ""))
        add(weightEntry)
    }
}