package com.marko.weightlosstracker.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.ui.core.DispatcherProvider
import com.marko.weightlosstracker.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _userLiveData = MutableLiveData<DataState<User?>>()
    val userLiveData: LiveData<DataState<User?>> = _userLiveData

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch(dispatchers.io) {
            userRepository.getUser().collect {
                _userLiveData.postValue(it)
            }
        }
    }
}