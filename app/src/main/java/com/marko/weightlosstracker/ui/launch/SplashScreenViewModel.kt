package com.marko.weightlosstracker.ui.launch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marko.weightlosstracker.domain.User
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    val userLiveData = MutableLiveData<DataState<User?>>()

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch(dispatchers.io) {
           userRepository.getUser().collect {
               userLiveData.postValue(it)
           }
        }
    }
}