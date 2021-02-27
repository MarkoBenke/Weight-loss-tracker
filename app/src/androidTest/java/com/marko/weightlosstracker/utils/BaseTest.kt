package com.marko.weightlosstracker.utils

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.marko.weightlosstracker.repository.FakeQuotesRepositoryAndroidTest
import com.marko.weightlosstracker.repository.FakeUserRepositoryAndroidTest
import com.marko.weightlosstracker.repository.FakeWeightEntryRepositoryAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class BaseTest: BaseUiTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var weightEntryRepository: FakeWeightEntryRepositoryAndroidTest

    @Inject
    lateinit var userRepository: FakeUserRepositoryAndroidTest

    @Inject
    lateinit var quotesRepository: FakeQuotesRepositoryAndroidTest

    lateinit var context: Context

    @Before
    open fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }
}