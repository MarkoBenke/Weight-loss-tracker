package com.marko.weightlosstracker.ui.main

import androidx.test.core.app.ActivityScenario
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.utils.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainActivityTest: BaseTest() {

    override fun setup() {
        super.setup()
        hiltRule.inject()
    }

    @Test
    fun checkMainScreenNavigation() {
        ActivityScenario.launch(MainActivity::class.java)

        isViewVisible(R.id.goalName)

        clickOnView(R.id.addEntry)
        isViewVisible(R.id.submitBtn)

        clickOnView(R.id.stats)
        isViewVisible(R.id.chartTitle)

        clickOnView(R.id.entryHistory)
        isViewVisible(R.id.entriesRecView)

        clickOnView(R.id.stats)
        isViewVisible(R.id.chartTitle)

        clickOnView(R.id.addEntry)
        isViewVisible(R.id.submitBtn)

        clickOnView(R.id.home)
        isViewVisible(R.id.goalName)
    }
}