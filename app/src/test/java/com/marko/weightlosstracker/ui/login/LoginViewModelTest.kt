package com.marko.weightlosstracker.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.other.FakeDispatcherProvider
import com.marko.weightlosstracker.other.MainCoroutineRule
import com.marko.weightlosstracker.other.getOrAwaitValueTest
import com.marko.weightlosstracker.repository.FakeAuthRepositoryTest
import com.marko.weightlosstracker.repository.FakeUserRepositoryTest
import com.marko.weightlosstracker.repository.FakeWeightEntryRepositoryTest
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: LoginViewModel

    @Test
    fun `get user, returns success`() {
        viewModel = LoginViewModel(
            FakeAuthRepositoryTest(),
            FakeUserRepositoryTest(),
            FakeWeightEntryRepositoryTest(),
            FakeDispatcherProvider()
        )

        viewModel.getUser()

        val value = viewModel.userLiveData.getOrAwaitValueTest()

        Truth.assertThat(value).isEqualTo(DataState.Success(DataGenerator.user))
    }

    @Test
    fun `get user, returns error`() {
        viewModel = LoginViewModel(
            FakeAuthRepositoryTest(),
            FakeUserRepositoryTest(shouldReturnError = true),
            FakeWeightEntryRepositoryTest(),
            FakeDispatcherProvider()
        )

        viewModel.getUser()

        val value = viewModel.userLiveData.getOrAwaitValueTest()

        Truth.assertThat(value).isEqualTo(DataState.Error("Error!"))
    }
}