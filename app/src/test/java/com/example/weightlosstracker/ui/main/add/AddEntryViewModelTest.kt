package com.example.weightlosstracker.ui.main.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weightlosstracker.other.DataGenerator
import com.example.weightlosstracker.other.FakeDispatcherProvider
import com.example.weightlosstracker.other.MainCoroutineRule
import com.example.weightlosstracker.other.getOrAwaitValueTest
import com.example.weightlosstracker.repository.FakeUserRepositoryTest
import com.example.weightlosstracker.repository.FakeWeightEntryRepositoryTest
import com.example.weightlosstracker.util.DataState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddEntryViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: AddEntryViewModel

    @Before
    fun setup() {
        viewModel = AddEntryViewModel(
            FakeWeightEntryRepositoryTest(),
            FakeUserRepositoryTest(),
            FakeDispatcherProvider()
        )
    }

    @Test
    fun `validate insert new weight entry, returns error`() {
        viewModel.insertNewEntry("", DataGenerator.weightEntry)

        val value = viewModel.insertWeightLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate insert new weight entry, returns success`() {
        viewModel.insertNewEntry("85", DataGenerator.weightEntry)

        val value = viewModel.insertWeightLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Success(Unit))
    }
}