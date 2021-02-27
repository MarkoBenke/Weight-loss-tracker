package com.marko.weightlosstracker.ui.main.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.marko.weightlosstracker.domain.User
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.util.BaseViewModel
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<DataState<User?>>() {

    val updateUserLiveData = MutableLiveData<Boolean>()

    override fun fetchInitialData() {
        viewModelScope.launch(dispatcherProvider.io) {
            userRepository.getUser().collect {
                modelLiveData.postValue(it)
            }
        }
    }

    fun updateUser(updatedTargetWeight: Float) {
        val value = modelLiveData.value as DataState.Success
        value.data?.targetWeight = updatedTargetWeight
        viewModelScope.launch(dispatcherProvider.io) {
            userRepository.updateUser(value.data!!)
            withContext(dispatcherProvider.main) {
                updateUserLiveData.postValue(true)
            }
        }
    }
}