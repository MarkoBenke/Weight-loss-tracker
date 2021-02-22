package com.example.weightlosstracker.ui.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weightlosstracker.other.DataGenerator
import com.example.weightlosstracker.other.FakeDispatcherProvider
import com.example.weightlosstracker.other.MainCoroutineRule
import com.example.weightlosstracker.other.getOrAwaitValueTest
import com.example.weightlosstracker.repository.FakeUserRepositoryTest
import com.example.weightlosstracker.util.DataState
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
        viewModel.insertUserToDb("", DataGenerator.user)

        val value = viewModel.insertUserLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate target weight, valid input, returns success`() {
        viewModel.insertUserToDb("70", DataGenerator.user)

        val value = viewModel.insertUserLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Success(Unit))
    }
}