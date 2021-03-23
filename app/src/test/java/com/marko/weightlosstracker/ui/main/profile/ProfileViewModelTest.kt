package com.marko.weightlosstracker.ui.main.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.other.FakeDispatcherProvider
import com.marko.weightlosstracker.other.MainCoroutineRule
import com.marko.weightlosstracker.other.getOrAwaitValueTest
import com.marko.weightlosstracker.repository.FakeUserRepositoryTest
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProfileViewModelTest {

    private val username = "Marko"
    private val userAge = "25"
    private val targetWeight = "95"
    private val emptyField = ""
    private val validationModel = UpdateUserValidationModel(
        username, userAge, targetWeight
    )

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ProfileViewModel

    @Test
    fun `validate update user, empty username, returns error`() {
        viewModel = ProfileViewModel(FakeUserRepositoryTest(), FakeDispatcherProvider())
        validationModel.username = emptyField
        viewModel.validate(validationModel)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate update user, empty age, returns error`() {
        viewModel = ProfileViewModel(FakeUserRepositoryTest(), FakeDispatcherProvider())
        validationModel.age = emptyField
        viewModel.validate(validationModel)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate update user, empty targetWeight, returns error`() {
        viewModel = ProfileViewModel(FakeUserRepositoryTest(), FakeDispatcherProvider())
        validationModel.targetWeight = emptyField
        viewModel.validate(validationModel)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate update user, empty fields, returns error`() {
        viewModel = ProfileViewModel(FakeUserRepositoryTest(), FakeDispatcherProvider())
        validationModel.apply {
            age = emptyField
            targetWeight = emptyField
            username = emptyField
        }

        viewModel.validate(validationModel)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Error())
    }

    @Test
    fun `validate update user, valid input, returns success`() {
        viewModel = ProfileViewModel(FakeUserRepositoryTest(), FakeDispatcherProvider())
        viewModel.validate(validationModel)

        val value = viewModel.validateLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Success(validationModel))
    }

    @Test
    fun `update user, returns success`() {
        viewModel = ProfileViewModel(FakeUserRepositoryTest(), FakeDispatcherProvider())
        viewModel.fetchInitialData()
        runBlocking {
            delay(500)
        }
        viewModel.updateUser(validationModel)

        val value = viewModel.updateUserLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Success(Unit))
    }

    @Test
    fun `fetch user, returns success`() {
        viewModel = ProfileViewModel(FakeUserRepositoryTest(), FakeDispatcherProvider())
        viewModel.fetchInitialData()
        val value = viewModel.modelLiveData.getOrAwaitValueTest()

        assertThat(value).isEqualTo(DataState.Success(DataGenerator.user))
    }
}