package com.marko.weightlosstracker.ui.main.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.other.FakeDispatcherProvider
import com.marko.weightlosstracker.other.MainCoroutineRule
import com.marko.weightlosstracker.other.getOrAwaitValueTest
import com.marko.weightlosstracker.repository.FakeQuotesRepositoryTest
import com.marko.weightlosstracker.repository.FakeWeightEntryRepositoryTest
import com.marko.weightlosstracker.util.DataState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeViewModel

    @Test
    fun `get quote, returns success`() {
        viewModel = HomeViewModel(
            FakeQuotesRepositoryTest(), FakeWeightEntryRepositoryTest(),
            FakeDispatcherProvider()
        )
        viewModel.fetchInitialData()
        val value = viewModel.quoteLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Success(DataGenerator.quote))
    }

    @Test
    fun `get quote, returns error`() {
        viewModel = HomeViewModel(
            FakeQuotesRepositoryTest(shouldReturnError = true),
            FakeWeightEntryRepositoryTest(),
            FakeDispatcherProvider()
        )
        viewModel.fetchInitialData()
        val value = viewModel.quoteLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Error("An unknown error occurred"))
    }

    @Test
    fun `get users stats, returns success`() {
        viewModel = HomeViewModel(
            FakeQuotesRepositoryTest(), FakeWeightEntryRepositoryTest(),
            FakeDispatcherProvider()
        )
        viewModel.fetchInitialData()
        val value = viewModel.modelLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataGenerator.stats)
    }
}