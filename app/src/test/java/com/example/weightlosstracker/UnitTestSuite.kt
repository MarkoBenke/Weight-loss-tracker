package com.example.weightlosstracker

import com.example.weightlosstracker.ui.launch.SplashScreenViewModelTest
import com.example.weightlosstracker.ui.main.add.AddEntryViewModelTest
import com.example.weightlosstracker.ui.main.history.EntryHistoryViewModelTest
import com.example.weightlosstracker.ui.main.home.HomeViewModelTest
import com.example.weightlosstracker.ui.onboarding.BasicInfoViewModelTest
import com.example.weightlosstracker.ui.onboarding.TargetWeightViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    SplashScreenViewModelTest::class, AddEntryViewModelTest::class,
    EntryHistoryViewModelTest::class, HomeViewModelTest::class, BasicInfoViewModelTest::class,
    TargetWeightViewModelTest::class
)
class UnitTestSuite