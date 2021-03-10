package com.marko.weightlosstracker.ui.main.home

import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.util.shortToString
import com.marko.weightlosstracker.utils.BaseTest
import com.marko.weightlosstracker.utils.DataGenerator
import com.marko.weightlosstracker.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class HomeFragmentTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    override fun setup() {
        super.setup()
        hiltRule.inject()
        launchFragmentInHiltContainer<HomeFragment> {}
    }

    @Test
    fun homeUiCheck() {
        val stats = DataGenerator.stats
        //stats
        isTextDisplayedInView(R.id.currentDate, stats.lastEntryDate)
        isTextDisplayedInView(R.id.startDate, stats.startDate)

        isTextDisplayedInView(
            R.id.currentWeight,
            context.getString(R.string.kg, stats.currentWeight.shortToString())
        )
        isTextDisplayedInView(
            R.id.currentBmi,
            context.getString(R.string.bmi_details_card, stats.bmi.shortToString())
        )
        isTextDisplayedInView(
            R.id.goalName,
            context.getString(R.string.goal_name, stats.targetWeight)
        )

        isTextDisplayedInView(
            R.id.startWeight,
            context.getString(R.string.kg, stats.startWeight.toString())
        )
        isTextDisplayedInView(
            R.id.startBmi,
            context.getString(R.string.bmi_details_card, stats.startBmi.shortToString())
        )

        //quote
        isTextDisplayedInView(R.id.quoteText, DataGenerator.quote.quote)
        isTextDisplayedInView(R.id.quoteAuthor, DataGenerator.quote.author)

        //bmi
        isTextVisible(R.string.bmi_chart_label)
        isViewVisible(R.id.segments)
    }
}