package com.marko.weightlosstracker.ui.main.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.other.FakeDispatcherProvider
import com.marko.weightlosstracker.other.MainCoroutineRule
import com.marko.weightlosstracker.other.getOrAwaitValueTest
import com.marko.weightlosstracker.repository.FakeUserRepositoryTest
import com.marko.weightlosstracker.repository.FakeWeightEntryRepositoryTest
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.parseDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddEntryViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: AddEntryViewModel

    @Test
    fun `validate insert new weight entry, returns error`() {
        initDefaultViewModel()
        viewModel.validate("")

        val value = viewModel.validationLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate insert new weight entry, returns success`() {
        val newWeight = "85"
        initDefaultViewModel()
        viewModel.validate(newWeight)

        val value = viewModel.validationLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Success(newWeight))
    }

    @Test
    fun `insert new weight entry, returns success`() {
        initDefaultViewModel()
        viewModel.insertWeightEntry(DataGenerator.weightEntry)

        val value = viewModel.insertWeightLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Success(Unit))
    }

    @Test
    fun `insert new weight entry, returns error`() {
        viewModel = AddEntryViewModel(
            FakeWeightEntryRepositoryTest(shouldReturnError = true), FakeUserRepositoryTest(),
            FakeDispatcherProvider()
        )
        viewModel.insertWeightEntry(DataGenerator.weightEntry)

        val value = viewModel.insertWeightLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Error())
    }

    @Test
    fun `get username, returns success`() {
        initDefaultViewModel()
        viewModel.fetchInitialData()

        val value = viewModel.usernameLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataGenerator.user.username)
    }

    @Test
    fun `get start date, returns success`() {
        val expectedResult = parseDate(DataGenerator.user.startDate)!!.time
        initDefaultViewModel()
        viewModel.fetchInitialData()

        val value = viewModel.modelLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(expectedResult)
    }

    private fun initDefaultViewModel() {
        viewModel = AddEntryViewModel(
            FakeWeightEntryRepositoryTest(), FakeUserRepositoryTest(),
            FakeDispatcherProvider()
        )
    }
}