package com.marko.weightlosstracker.ui.main

import androidx.test.core.app.ActivityScenario
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.utils.BaseTest
import com.marko.weightlosstracker.utils.DataGenerator
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainActivityTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    override fun setup() {
        super.setup()
        hiltRule.inject()
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun checkMainScreenNavigation() {
        isViewVisible(R.id.goalName)

        clickOnView(R.id.addEntry)
        sleepMedium()
        isViewVisible(R.id.submitBtn)

        clickOnView(R.id.stats)
        isViewVisible(R.id.chartTitle)

        navigateToEntries()

        clickOnView(R.id.stats)
        isViewVisible(R.id.chartTitle)

        clickOnView(R.id.addEntry)
        sleepMedium()
        isViewVisible(R.id.submitBtn)

        clickOnView(R.id.home)
        isViewVisible(R.id.goalName)
    }

    @Test
    fun deleteEntryFromDetailsNavigatesBack() {
        navigateToEntries()
        checkRecyclerViewItemCount(R.id.entriesRecView, 3)

        clickOnRecyclerViewItem(R.id.entriesRecView, R.id.currentWeight, FIRST_POSITION)

        isViewNotVisible(R.id.bottomNav)
        isViewVisible(R.id.delete)

        clickOnView(R.id.delete)
        isViewVisible(R.id.bottomNav)
        checkRecyclerViewItemCount(R.id.entriesRecView, 2)
    }

    @Test
    fun updateEntryFromDetailsNavigatesBack() {
        val newWeightValue = "92.5"
        val newDescriptionValue = "new desc"
        val newWaistSize = "50"

        navigateToEntries()

        checkTextOnRecyclerViewItem(
            R.id.entriesRecView,
            R.id.currentWeight,
            FIRST_POSITION,
            context.getString(R.string.kg, DataGenerator.weightEntry.currentWeight.toString())
        )
        checkTextOnRecyclerViewItem(
            R.id.entriesRecView,
            R.id.waistSize,
            FIRST_POSITION,
            context.getString(R.string.cm, DataGenerator.weightEntry.waistSize.toString())
        )
        checkTextOnRecyclerViewItem(
            R.id.entriesRecView,
            R.id.description,
            FIRST_POSITION,
            DataGenerator.weightEntry.description
        )

        clickOnRecyclerViewItem(R.id.entriesRecView, R.id.currentWeight, FIRST_POSITION)

        isViewNotVisible(R.id.bottomNav)

        replaceTextOnInputLayout(R.id.newWeight, newWeightValue)
        replaceTextOnInputLayout(R.id.waistSize, newWaistSize)
        replaceTextOnInputLayout(R.id.description, newDescriptionValue)

        clickOnView(R.id.submit)
        isViewVisible(R.id.bottomNav)

        checkTextOnRecyclerViewItem(
            R.id.entriesRecView,
            R.id.currentWeight,
            FIRST_POSITION,
            context.getString(R.string.kg, newWeightValue)
        )
        checkTextOnRecyclerViewItem(
            R.id.entriesRecView,
            R.id.waistSize,
            FIRST_POSITION,
            context.getString(R.string.cm, newWaistSize)
        )
        checkTextOnRecyclerViewItem(
            R.id.entriesRecView,
            R.id.description,
            FIRST_POSITION,
            newDescriptionValue
        )
    }

    @Test
    fun openInitialEntryDeleteDisabled() {
        navigateToEntries()

        clickOnRecyclerViewItem(R.id.entriesRecView, R.id.currentWeight, THIRD_POSITION)

        isViewNotVisible(R.id.bottomNav)
        isButtonDisabled(R.id.delete)
    }

    private fun navigateToEntries() {
        clickOnView(R.id.entryHistory)
        isViewVisible(R.id.entriesRecView)
    }
}