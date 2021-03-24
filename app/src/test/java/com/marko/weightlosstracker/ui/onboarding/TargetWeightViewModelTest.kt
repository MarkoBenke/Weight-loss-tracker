package com.marko.weightlosstracker.ui.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.other.FakeDispatcherProvider
import com.marko.weightlosstracker.other.MainCoroutineRule
import com.marko.weightlosstracker.other.getOrAwaitValueTest
import com.marko.weightlosstracker.repository.FakeUserRepositoryTest
import com.marko.weightlosstracker.util.DataState
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

    @Before
    fun setup() {
        viewModel = TargetWeightViewModel(FakeUserRepositoryTest(), FakeDispatcherProvider())
    }

    @Test
    fun `validate target weight, invalid input, returns error`() {
        viewModel.createUser("", DataGenerator.user)

        val value = viewModel.modelLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate target weight, valid input, returns success`() {
        viewModel.createUser("70", DataGenerator.user)

        val value = viewModel.modelLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Success(Unit))
    }
}