package com.marko.weightlosstracker

import com.marko.weightlosstracker.ui.launch.SplashScreenActivityTest
import com.marko.weightlosstracker.ui.main.MainActivityTest
import com.marko.weightlosstracker.ui.main.add.AddEntryFragmentTest
import com.marko.weightlosstracker.ui.main.history.EntryHistoryFragmentTest
import com.marko.weightlosstracker.ui.main.home.HomeFragmentTest
import com.marko.weightlosstracker.ui.main.stats.StatsFragmentTest
import com.marko.weightlosstracker.ui.onboarding.BasicInfoFragmentTest
import com.marko.weightlosstracker.ui.onboarding.OnBoardingActivityTest
import com.marko.weightlosstracker.ui.onboarding.TargetWeightFragmentTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    OnBoardingActivityTest::class,
    SplashScreenActivityTest::class,
    MainActivityTest::class,
    AddEntryFragmentTest::class,
    EntryHistoryFragmentTest::class,
    HomeFragmentTest::class,
    StatsFragmentTest::class,
    BasicInfoFragmentTest::class,
    TargetWeightFragmentTest::class
)
class AndroidTestSuite