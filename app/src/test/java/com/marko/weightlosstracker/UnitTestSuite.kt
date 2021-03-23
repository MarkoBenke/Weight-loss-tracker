package com.marko.weightlosstracker

import com.marko.weightlosstracker.ui.launch.SplashScreenViewModelTest
import com.marko.weightlosstracker.ui.login.LoginViewModelTest
import com.marko.weightlosstracker.ui.main.add.AddEntryViewModelTest
import com.marko.weightlosstracker.ui.main.details.EntryDetailsViewModelTest
import com.marko.weightlosstracker.ui.main.history.EntryHistoryViewModelTest
import com.marko.weightlosstracker.ui.main.home.HomeViewModelTest
import com.marko.weightlosstracker.ui.main.profile.ProfileViewModelTest
import com.marko.weightlosstracker.ui.onboarding.BasicProfileViewModelTest
import com.marko.weightlosstracker.ui.onboarding.TargetWeightViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    SplashScreenViewModelTest::class, AddEntryViewModelTest::class,
    EntryHistoryViewModelTest::class, HomeViewModelTest::class, BasicProfileViewModelTest::class,
    TargetWeightViewModelTest::class, EntryDetailsViewModelTest::class, ProfileViewModelTest::class,
    LoginViewModelTest::class
)
class UnitTestSuite