package com.example.weightlosstracker.ui.main.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weightlosstracker.DataGenerator
import com.example.weightlosstracker.FakeDispatcherProvider
import com.example.weightlosstracker.MainCoroutineRule
import com.example.weightlosstracker.getOrAwaitValueTest
import com.example.weightlosstracker.repository.FakeQuotesRepositoryTest
import com.example.weightlosstracker.repository.FakeWeightEntryRepositoryTest
import com.example.weightlosstracker.util.DataState
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
            FakeQuotesRepositoryTest(), FakeWeightEntryRepositoryTest(), FakeDispatcherProvider()
        )

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

        val value = viewModel.quoteLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Error("An unknown error occurred"))
    }

    @Test
    fun `get users stats, returns success`() {
        viewModel = HomeViewModel(
            FakeQuotesRepositoryTest(), FakeWeightEntryRepositoryTest(), FakeDispatcherProvider()
        )

        val value = viewModel.userStatsLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataGenerator.stats)
    }
}