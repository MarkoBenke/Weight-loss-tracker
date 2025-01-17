package com.marko.weightlosstracker.ui.launch

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.marko.weightlosstracker.di.FakeRepositoryModule
import com.marko.weightlosstracker.ui.login.LoginActivity
import com.marko.weightlosstracker.ui.main.MainActivity
import com.marko.weightlosstracker.utils.BaseTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class SplashScreenActivityTest: BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    override fun setup() {
        super.setup()
        Intents.init()
    }

    @After
    fun teardown() {
        Intents.release()
        FakeRepositoryModule.shouldReturnError = false
    }

    @Test
    fun checkNavigationHandlingIfUserSignedInReturnsError() {
        FakeRepositoryModule.shouldReturnError = true
        hiltRule.inject()

        ActivityScenario.launch(SplashScreenActivity::class.java)
        runBlocking {
            delay(SPLASH_SCREEN_TIME_OUT)
        }
        intended(hasComponent(LoginActivity::class.java.name))
    }

    @Test
    fun checkNavigationHandlingIfUserSignedInReturnsSuccess() {
        FakeRepositoryModule.shouldReturnError = false
        hiltRule.inject()
        ActivityScenario.launch(SplashScreenActivity::class.java)
        runBlocking {
            delay(SPLASH_SCREEN_TIME_OUT)
        }
        intended(hasComponent(MainActivity::class.java.name))
    }

    companion object {
        const val SPLASH_SCREEN_TIME_OUT = 3500L
    }
}