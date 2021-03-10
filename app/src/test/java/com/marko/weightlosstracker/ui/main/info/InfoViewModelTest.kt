package com.marko.weightlosstracker.ui.main.info

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.other.FakeDispatcherProvider
import com.marko.weightlosstracker.other.MainCoroutineRule
import com.marko.weightlosstracker.other.getOrAwaitValueTest
import com.marko.weightlosstracker.repository.FakeWeightEntryRepositoryTest
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.getCurrentDate
import com.google.common.truth.Truth.assertThat
import com.marko.weightlosstracker.repository.FakeUserRepositoryTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class InfoViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: InfoViewModel

    @Before
    fun setup() {
        viewModel = InfoViewModel(FakeUserRepositoryTest(), FakeDispatcherProvider())
    }

    @Test
    fun `validate update user target weight, returns error`() {
        viewModel.updateUser("")

        val value = viewModel.updateUserLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate update user target weight, returns success`() {
        viewModel.fetchInitialData()
        runBlocking {
            delay(1000)
        }
        viewModel.updateUser("95")

        val value = viewModel.updateUserLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Success(Unit))
    }

    @Test
    fun `fetch user, returns success`() {
        viewModel.fetchInitialData()
        val value = viewModel.modelLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Success(DataGenerator.user))
    }
}