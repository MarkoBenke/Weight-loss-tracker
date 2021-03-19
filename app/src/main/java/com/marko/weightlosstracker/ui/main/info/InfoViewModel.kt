package com.marko.weightlosstracker.ui.main.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.ui.core.BaseViewModel
import com.marko.weightlosstracker.ui.core.DispatcherProvider
import com.marko.weightlosstracker.util.DataState
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

    private val _updateUserLiveData = MutableLiveData<DataState<Unit>>()
    val updateUserLiveData: LiveData<DataState<Unit>> = _updateUserLiveData

    override fun fetchInitialData() {
        viewModelScope.launch(dispatcherProvider.io) {
            userRepository.getUser().collect {
                modelLiveData.postValue(it)
            }
        }
    }

    fun updateUser(updatedTargetWeight: String) {
        if (updatedTargetWeight.isEmpty()) {
            _updateUserLiveData.postValue(DataState.Error())
        } else {
            val value = modelLiveData.value as DataState.Success?
            value?.data?.targetWeight = updatedTargetWeight.toFloat()
            viewModelScope.launch(dispatcherProvider.io) {
                userRepository.updateUser(value?.data!!).collect {
                    _updateUserLiveData.postValue(it)
                }
            }
        }
    }
}