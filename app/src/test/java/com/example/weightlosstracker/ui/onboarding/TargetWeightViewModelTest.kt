package com.example.weightlosstracker.ui.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weightlosstracker.FakeDispatcherProvider
import com.example.weightlosstracker.MainCoroutineRule
import com.example.weightlosstracker.getOrAwaitValueTest
import com.example.weightlosstracker.repository.FakeUserRepository
import com.example.weightlosstracker.util.DispatcherProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TargetWeightViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: TargetWeightViewModel
    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setup() {
        dispatcherProvider = FakeDispatcherProvider()
        viewModel = TargetWeightViewModel(FakeUserRepository(), dispatcherProvider)
    }

    @Test
    fun `validate target weight, invalid input, returns error`() {
        viewModel.validate("")

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value).isFalse()
    }

    @Test
    fun `validate target weight, valid input, returns success`() {
        viewModel.validate("70")

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value).isTrue()
    }
}