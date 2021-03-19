package com.marko.weightlosstracker.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marko.weightlosstracker.model.Gender
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.ui.core.DispatcherProvider
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TargetWeightViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _insertUserLiveData = MutableLiveData<Event<DataState<Unit>>>()
    val insertUserLiveData: LiveData<Event<DataState<Unit>>> = _insertUserLiveData

    fun createUser(targetWeight: String, user: User?) {
        if (targetWeight.isEmpty()) {
            _insertUserLiveData.postValue(Event(DataState.Error()))
        } else {
            user?.let {
                viewModelScope.launch(dispatchers.io) {
                    userRepository.insertUser(it).collect {
                        _insertUserLiveData.postValue(Event(it))
                    }
                }
            }
        }
    }

    fun generateIdealWeight(user: User?): String {
        return user?.let {
            val minWeight: Float
            val maxWeight: Float
            if (it.gender == Gender.MALE) {
                minWeight = it.height - 103
                maxWeight = it.height - 97
            } else {
                minWeight = it.height - 110
                maxWeight = it.height - 102
            }

            "$minWeight kg - $maxWeight kg"
        } ?: ""
    }
}