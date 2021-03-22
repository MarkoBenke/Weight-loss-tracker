package com.marko.weightlosstracker.ui.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marko.weightlosstracker.other.MainCoroutineRule
import com.marko.weightlosstracker.other.getOrAwaitValueTest
import com.marko.weightlosstracker.util.DataState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class BasicProfileViewModelTest {

    private val username = "Marko"
    private val userAge = "25"
    private val userHeight = "175"
    private val userWeight = "95"
    private val emptyField = ""
    private val validationModel = BasicInfoValidationModel(
        username, userHeight, userAge, userWeight
    )

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
    fun `validate basic info, empty username, returns error`() {
        validationModel.username = emptyField
        viewModel.validate(validationModel)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate basic info, empty height, returns error`() {
        validationModel.height = emptyField
        viewModel.validate(validationModel)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate basic info, empty age, returns error`() {
        validationModel.age = emptyField
        viewModel.validate(validationModel)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate basic info, empty weight, returns error`() {
        validationModel.currentWeight = emptyField
        viewModel.validate(validationModel)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate basic info, empty fields, returns error`() {
        validationModel.apply {
            age = emptyField
            username = emptyField
            currentWeight = emptyField
            height = emptyField
        }
        viewModel.validate(validationModel)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate basic info, valid input, returns success`() {
        viewModel.validate(validationModel)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()).isEqualTo(DataState.Success(Unit))
    }
}