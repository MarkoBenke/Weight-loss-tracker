package com.marko.weightlosstracker.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.repository.auth.AuthRepository
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.marko.weightlosstracker.ui.core.DispatcherProvider
import com.marko.weightlosstracker.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val weightEntryRepository: WeightEntryRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _userLiveData = MutableLiveData<DataState<User?>>()
    val userLiveData: LiveData<DataState<User?>> = _userLiveData

    private val _authUserLiveData = MutableLiveData<DataState<AuthResult?>>()
    val authUserLiveData: LiveData<DataState<AuthResult?>> = _authUserLiveData

    fun loginWithGoogle(credential: AuthCredential) {
        viewModelScope.launch(dispatcherProvider.io) {
            authRepository.signInUser(credential).collect {
                _authUserLiveData.postValue(it)
            }
        }
    }

    fun syncDataAndFetchUser() {
        viewModelScope.launch(dispatcherProvider.io) {
            userRepository.syncUserData().collect {
                weightEntryRepository.syncEntriesData().collect {
                    userRepository.getUser().collect { user ->
                        _userLiveData.postValue(user)
                    }
                }
            }
        }
    }
}