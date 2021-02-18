package com.example.weightlosstracker

import com.example.weightlosstracker.ui.launch.SplashScreenActivityTest
import com.example.weightlosstracker.ui.main.MainActivityTest
import com.example.weightlosstracker.ui.main.add.AddEntryFragmentTest
import com.example.weightlosstracker.ui.main.history.EntryHistoryFragmentTest
import com.example.weightlosstracker.ui.main.home.HomeFragmentTest
import com.example.weightlosstracker.ui.main.stats.StatsFragmentTest
import com.example.weightlosstracker.ui.onboarding.BasicInfoFragmentTest
import com.example.weightlosstracker.ui.onboarding.OnBoardingActivityTest
import com.example.weightlosstracker.ui.onboarding.TargetWeightFragmentTest
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