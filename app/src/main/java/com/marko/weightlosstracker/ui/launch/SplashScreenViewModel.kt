package com.marko.weightlosstracker.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class SplashScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val weightEntryRepository: WeightEntryRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _userLiveData = MutableLiveData<DataState<User?>>()
    val userLiveData: LiveData<DataState<User?>> = _userLiveData

    private val _isUserSignedInLiveData = MutableLiveData<Boolean>()
    val isUserSignedInLiveData: LiveData<Boolean> = _isUserSignedInLiveData

    init {
        isUserSignedIn()
    }

    fun syncDataAndFetchUser() {
        viewModelScope.launch(dispatchers.io) {
            userRepository.syncUserData().collect {
                weightEntryRepository.syncEntriesData().collect {
                    userRepository.getUser().collect { user ->
                        _userLiveData.postValue(user)
                    }
                }
            }
        }
    }

    private fun isUserSignedIn() =
        _isUserSignedInLiveData.postValue(authRepository.isUserSignedIn())
}