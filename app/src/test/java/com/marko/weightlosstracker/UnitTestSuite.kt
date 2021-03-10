package com.marko.weightlosstracker

import com.marko.weightlosstracker.ui.launch.SplashScreenViewModelTest
import com.marko.weightlosstracker.ui.main.add.AddEntryViewModelTest
import com.marko.weightlosstracker.ui.main.details.EntryDetailsViewModelTest
import com.marko.weightlosstracker.ui.main.history.EntryHistoryViewModelTest
import com.marko.weightlosstracker.ui.main.home.HomeViewModelTest
import com.marko.weightlosstracker.ui.main.info.InfoViewModel
import com.marko.weightlosstracker.ui.main.info.InfoViewModelTest
import com.marko.weightlosstracker.ui.onboarding.BasicInfoViewModelTest
import com.marko.weightlosstracker.ui.onboarding.TargetWeightViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    SplashScreenViewModelTest::class, AddEntryViewModelTest::class,
    EntryHistoryViewModelTest::class, HomeViewModelTest::class, BasicInfoViewModelTest::class,
    TargetWeightViewModelTest::class, EntryDetailsViewModelTest::class, InfoViewModelTest::class
)
class UnitTestSuite