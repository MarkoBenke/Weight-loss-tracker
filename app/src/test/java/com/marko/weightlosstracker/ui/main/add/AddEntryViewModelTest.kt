package com.marko.weightlosstracker.ui.main.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.other.FakeDispatcherProvider
import com.marko.weightlosstracker.other.MainCoroutineRule
import com.marko.weightlosstracker.other.getOrAwaitValueTest
import com.marko.weightlosstracker.repository.FakeUserRepositoryTest
import com.marko.weightlosstracker.repository.FakeWeightEntryRepositoryTest
import com.marko.weightlosstracker.util.DataState
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
        viewModel.validate("", DataGenerator.weightEntry)

        val value = viewModel.validationLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate insert new weight entry, returns success`() {
        viewModel.validate("85", DataGenerator.weightEntry)

        val value = viewModel.validationLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Success(Unit))
    }
}