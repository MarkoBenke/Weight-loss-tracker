package com.marko.weightlosstracker.utils

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.marko.weightlosstracker.repository.FakeAuthRepositoryAndroidTest
import com.marko.weightlosstracker.repository.FakeQuotesRepositoryAndroidTest
import com.marko.weightlosstracker.repository.FakeUserRepositoryAndroidTest
import com.marko.weightlosstracker.repository.FakeWeightEntryRepositoryAndroidTest
import org.junit.Before
import javax.inject.Inject

abstract class BaseTest : BaseUiTest() {

    @Inject
    lateinit var weightEntryRepository: FakeWeightEntryRepositoryAndroidTest

    @Inject
    lateinit var userRepository: FakeUserRepositoryAndroidTest

    @Inject
    lateinit var quotesRepository: FakeQuotesRepositoryAndroidTest

    @Inject
    lateinit var authRepository: FakeAuthRepositoryAndroidTest

    lateinit var context: Context

    @Before
    open fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }
}