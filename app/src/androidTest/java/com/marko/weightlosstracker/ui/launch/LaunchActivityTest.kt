package com.marko.weightlosstracker.ui.launch

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.ui.onboarding.OnBoardingActivity
import com.marko.weightlosstracker.utils.BaseTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class LaunchActivityTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    override fun setup() {
        super.setup()
        Intents.init()
        hiltRule.inject()
    }

    @After
    fun teardown() {
        Intents.release()
    }

    @Test
    fun uiCheck() {
        ActivityScenario.launch(LaunchActivity::class.java)

        isViewVisible(R.id.appTitle)
        isViewVisible(R.id.appDesc)

        clickOnView(R.id.startButton)
        intended(hasComponent(OnBoardingActivity::class.java.name))
    }
}