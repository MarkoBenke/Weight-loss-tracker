package com.marko.weightlosstracker.ui.main.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.other.FakeDispatcherProvider
import com.marko.weightlosstracker.other.MainCoroutineRule
import com.marko.weightlosstracker.other.getOrAwaitValueTest
import com.marko.weightlosstracker.repository.FakeWeightEntryRepositoryTest
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.getCurrentDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EntryHistoryViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: EntryHistoryViewModel

    @Before
    fun setup() {
        viewModel = EntryHistoryViewModel(FakeWeightEntryRepositoryTest(), FakeDispatcherProvider())
    }

    @Test
    fun `check item count, return success`() {
        viewModel.fetchInitialData()
        val value = viewModel.modelLiveData.getOrAwaitValueTest() as DataState.Success

        assertThat(value.data).hasSize(3)
    }

    @Test
    fun `delete item, check count`() {
        viewModel.fetchInitialData()
        viewModel.deleteEntry(DataGenerator.weightEntry)

        val value = viewModel.modelLiveData.getOrAwaitValueTest() as DataState.Success

        assertThat(value.data).hasSize(2)
    }

    @Test
    fun `check item sorting, return success`() {
        viewModel.fetchInitialData()
        val value = viewModel.modelLiveData.getOrAwaitValueTest() as DataState.Success
        val entries = DataGenerator.listOfEntries
        val resultEntries = value.data

        assertThat(entries[2].date).isEqualTo(getCurrentDate())
        assertThat(resultEntries[0].date).isEqualTo(getCurrentDate())
    }
}