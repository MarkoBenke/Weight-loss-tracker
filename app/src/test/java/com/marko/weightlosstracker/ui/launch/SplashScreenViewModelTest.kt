package com.marko.weightlosstracker.ui.launch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.other.FakeDispatcherProvider
import com.marko.weightlosstracker.other.MainCoroutineRule
import com.marko.weightlosstracker.other.getOrAwaitValueTest
import com.marko.weightlosstracker.repository.FakeUserRepositoryTest
import com.marko.weightlosstracker.util.DataState
import com.google.common.truth.Truth.assertThat
import com.marko.weightlosstracker.repository.FakeAuthRepositoryTest
import com.marko.weightlosstracker.repository.FakeWeightEntryRepositoryTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashScreenViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SplashScreenViewModel

    @Test
    fun `get user, returns success`() {
        viewModel = SplashScreenViewModel(
            FakeAuthRepositoryTest(),
            FakeUserRepositoryTest(),
            FakeWeightEntryRepositoryTest(),
            FakeDispatcherProvider()
        )

        viewModel.syncDataAndFetchUser()

        val value = viewModel.userLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Success(DataGenerator.user))
    }

    @Test
    fun `get user, returns error`() {
        viewModel = SplashScreenViewModel(
            FakeAuthRepositoryTest(),
            FakeUserRepositoryTest(shouldReturnError = true),
            FakeWeightEntryRepositoryTest(),
            FakeDispatcherProvider()
        )

        viewModel.syncDataAndFetchUser()

        val value = viewModel.userLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Error("Error!"))
    }

    @Test
    fun `is user signed in, returns success`() {
        viewModel = SplashScreenViewModel(
            FakeAuthRepositoryTest(),
            FakeUserRepositoryTest(),
            FakeWeightEntryRepositoryTest(),
            FakeDispatcherProvider()
        )

        val value = viewModel.isUserSignedInLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Success(Unit))
    }

    @Test
    fun `is user signed in, returns error`() {
        viewModel = SplashScreenViewModel(
            FakeAuthRepositoryTest(shouldReturnError = true),
            FakeUserRepositoryTest(),
            FakeWeightEntryRepositoryTest(),
            FakeDispatcherProvider()
        )

        val value = viewModel.isUserSignedInLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Error("Error!"))
    }
}