package com.example.weightlosstracker.ui.launch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weightlosstracker.domain.User
import com.example.weightlosstracker.repository.user.UserRepository
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.DispatcherProvider
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