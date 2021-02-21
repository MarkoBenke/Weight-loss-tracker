package com.example.weightlosstracker.ui.main.stats

import com.example.weightlosstracker.R
import com.example.weightlosstracker.domain.WeightEntry
import com.example.weightlosstracker.util.makeShort
import com.example.weightlosstracker.utils.BaseTest
import com.example.weightlosstracker.utils.DataGenerator
import com.example.weightlosstracker.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class StatsFragmentTest: BaseTest() {

    override fun setup() {
        super.setup()
        hiltRule.inject()
        launchFragmentInHiltContainer<StatsFragment> {  }
    }

    @Test
    fun checkStatsUi() {
        val stats = DataGenerator.stats
        val listOfEntries = DataGenerator.listOfEntries
        val maxEntry = listOfEntries.maxByOrNull { weightEntry: WeightEntry ->
            weightEntry.currentWeight
        }
        val minEntry = listOfEntries.minByOrNull { weightEntry: WeightEntry ->
            weightEntry.currentWeight
        }

        isTextDisplayedInView(R.id.chartTitle, R.string.chart_title)
        isViewVisible(R.id.lineChart)
        isTextDisplayedInView(R.id.progressTitle, R.string.progress_title)

        isTextDisplayedInView(R.id.currentWeight, context.resources.getString(R.string.kg, stats.currentWeight.toString().makeShort()))
        isTextDisplayedInView(R.id.targetWeight, context.resources.getString(R.string.kg, stats.targetWeight.toString().makeShort()))
        isTextDisplayedInView(R.id.totalLoss, context.resources.getString(R.string.kg, stats.totalLoss.toString().makeShort()))
        isTextDisplayedInView(R.id.remaining, context.resources.getString(R.string.kg, stats.remaining.toString().makeShort()))
        isTextDisplayedInView(R.id.bmiCategory, context.resources.getString(R.string.bmi_underweight))
        isTextDisplayedInView(R.id.totalEntries, "${listOfEntries.size}")
        isTextDisplayedInView(R.id.worstRecord, context.resources.getString(R.string.kg, maxEntry?.currentWeight.toString()))
        isTextDisplayedInView(R.id.bestRecord, context.resources.getString(R.string.kg, minEntry?.currentWeight.toString()))
    }
}