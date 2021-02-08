package com.example.weightlosstracker.ui.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.weightlosstracker.R
import com.example.weightlosstracker.di.RepositoryModule
import com.example.weightlosstracker.repository.FakeQuotesRepository
import com.example.weightlosstracker.repository.FakeUserRepositoryAndroidTest
import com.example.weightlosstracker.repository.FakeWeightEntryRepository
import com.example.weightlosstracker.repository.qoutes.QuotesRepository
import com.example.weightlosstracker.repository.user.UserRepository
import com.example.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.example.weightlosstracker.ui.main.MainActivity
import com.example.weightlosstracker.ui.main.home.HomeFragment
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test


@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class OnBoardingActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue @JvmField
    val weightEntryRepository: WeightEntryRepository = FakeWeightEntryRepository()

    @BindValue @JvmField
    val userRepository: UserRepository = FakeUserRepositoryAndroidTest()

    @BindValue @JvmField
    val quotesRepository: QuotesRepository = FakeQuotesRepository()

    @Test
    fun test() {
        ActivityScenario.launch(MainActivity::class.java)

        // TODO check custom method for launch fragment in container with hilt Lackner YT
//        launchFragmentInContainer<HomeFragment>()
//        onView(withId(R.id.addEntry)).perform(click())
    }
}