package com.example.weightlosstracker.ui.launch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weightlosstracker.other.DataGenerator
import com.example.weightlosstracker.other.FakeDispatcherProvider
import com.example.weightlosstracker.other.MainCoroutineRule
import com.example.weightlosstracker.other.getOrAwaitValueTest
import com.example.weightlosstracker.repository.FakeUserRepositoryTest
import com.example.weightlosstracker.util.DataState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashScreenViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule =
        MainCoroutineRule()

    private lateinit var viewModel: SplashScreenViewModel

    @Test
    fun `get user, returns success`() {
        viewModel = SplashScreenViewModel(FakeUserRepositoryTest(),
            FakeDispatcherProvider()
        )

        val value = viewModel.userLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Success(DataGenerator.user))
    }

    @Test
    fun `get user, returns error`() {
        viewModel = SplashScreenViewModel(
            FakeUserRepositoryTest(shouldReturnError = true),
            FakeDispatcherProvider()
        )

        val value = viewModel.userLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Error("Error!"))
    }
}