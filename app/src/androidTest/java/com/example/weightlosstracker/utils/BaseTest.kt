package com.example.weightlosstracker.utils

import com.example.weightlosstracker.repository.FakeQuotesRepository
import com.example.weightlosstracker.repository.FakeUserRepositoryAndroidTest
import com.example.weightlosstracker.repository.FakeWeightEntryRepository
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

abstract class BaseTest: BaseUiTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var weightEntryRepository: FakeWeightEntryRepository

    @Inject
    lateinit var userRepository: FakeUserRepositoryAndroidTest

    @Inject
    lateinit var quotesRepository: FakeQuotesRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }
}