package com.marko.weightlosstracker.ui.main.profile

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
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<DataState<User?>>() {

    private val _updateUserLiveData = MutableLiveData<DataState<Unit>>()
    val updateUserLiveData: LiveData<DataState<Unit>> = _updateUserLiveData

    private val _validateLiveData = MutableLiveData<DataState<UpdateUserValidationModel>>()
    val validateLiveData: LiveData<DataState<UpdateUserValidationModel>> = _validateLiveData

    override fun fetchInitialData() {
        viewModelScope.launch(dispatcherProvider.io) {
            userRepository.getUser().collect {
                modelLiveData.postValue(it)
            }
        }
    }

    fun validate(validationModel: UpdateUserValidationModel) {
        if (validationModel.isValid()) {
            _validateLiveData.postValue(DataState.Success(validationModel))
        } else {
            _validateLiveData.postValue(DataState.Error())
        }
    }

    fun updateUser(model: UpdateUserValidationModel) {
        val userResult = modelLiveData.value as DataState.Success?
        userResult?.data?.let {
            it.targetWeight = model.targetWeight.toFloat()
            it.age = model.age.toInt()
            it.username = model.username
            viewModelScope.launch(dispatcherProvider.io) {
                userRepository.updateUser(it).collect {
                    _updateUserLiveData.postValue(it)
                }
            }
        } ?: _updateUserLiveData.postValue(DataState.Error())
    }
}