package com.example.weightlosstracker.ui.main.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weightlosstracker.other.DataGenerator
import com.example.weightlosstracker.other.FakeDispatcherProvider
import com.example.weightlosstracker.other.MainCoroutineRule
import com.example.weightlosstracker.other.getOrAwaitValueTest
import com.example.weightlosstracker.repository.FakeWeightEntryRepositoryTest
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.getCurrentDate
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
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
    fun `delete and undo item, check count`() {
        viewModel.fetchInitialData()
        viewModel.deleteEntry(DataGenerator.weightEntry)
        viewModel.reverseDeletion(DataGenerator.weightEntry)

        runBlocking {
            delay(1000)
        }
        val value = viewModel.modelLiveData.getOrAwaitValueTest() as DataState.Success

        assertThat(value.data).hasSize(3)
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