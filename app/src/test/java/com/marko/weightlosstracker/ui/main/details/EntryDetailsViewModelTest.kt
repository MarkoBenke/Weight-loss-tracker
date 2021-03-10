package com.marko.weightlosstracker.ui.main.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.other.FakeDispatcherProvider
import com.marko.weightlosstracker.other.MainCoroutineRule
import com.marko.weightlosstracker.other.getOrAwaitValueTest
import com.marko.weightlosstracker.repository.FakeWeightEntryRepositoryTest
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EntryDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: EntryDetailsViewModel

    @Before
    fun setup() {
        viewModel = EntryDetailsViewModel(FakeWeightEntryRepositoryTest(), FakeDispatcherProvider())
    }

    @Test
    fun `validate update weight entry, returns error`() {
        viewModel.validate("")

        val value = viewModel.validation.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate update weight entry, returns success`() {
        viewModel.validate("95")

        val value = viewModel.validation.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Success(Unit))
    }

    @Test
    fun `update weight entry, returns success`() {
        viewModel.update(DataGenerator.weightEntry)

        val value = viewModel.weightEntryAction.getOrAwaitValueTest()

        assertThat(value).isEqualTo(true)
    }

    @Test
    fun `delete weight entry, returns success`() {
        viewModel.delete(DataGenerator.weightEntry)

        val value = viewModel.weightEntryAction.getOrAwaitValueTest()

        assertThat(value).isEqualTo(true)
    }
}