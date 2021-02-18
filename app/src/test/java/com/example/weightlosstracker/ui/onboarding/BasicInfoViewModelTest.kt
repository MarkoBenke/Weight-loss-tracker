package com.example.weightlosstracker.ui.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weightlosstracker.MainCoroutineRule
import com.example.weightlosstracker.getOrAwaitValueTest
import com.example.weightlosstracker.util.DataState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class BasicInfoViewModelTest {

    private val userAge = "25"
    private val userHeight = "175"
    private val userWeight = "95"
    private val emptyField = ""

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: BasicInfoViewModel

    @Before
    fun setup() {
        viewModel = BasicInfoViewModel()
    }

    @Test
    fun `validate basic info, empty height, returns error`() {
        viewModel.validate(emptyField, userAge, userWeight)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate basic info, empty age, returns error`() {
        viewModel.validate(userHeight, emptyField, userWeight)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate basic info, empty weight, returns error`() {
        viewModel.validate(userHeight, userAge, emptyField)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate basic info, empty fields, returns error`() {
        viewModel.validate(emptyField, emptyField, emptyField)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate basic info, valid input, returns success`() {
        viewModel.validate(userHeight, userAge, userWeight)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Success(Unit))
    }
}