package com.marko.weightlosstracker.ui.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marko.weightlosstracker.domain.Gender
import com.marko.weightlosstracker.domain.User
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.DispatcherProvider
import com.marko.weightlosstracker.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TargetWeightViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    val insertUserLiveData = MutableLiveData<Event<DataState<Unit>>>()

    fun insertUserToDb(targetWeight: String, user: User?) {
        if (targetWeight.isEmpty()) {
            insertUserLiveData.postValue(Event(DataState.Error()))
        } else {
            user?.let {
                viewModelScope.launch(dispatchers.io) {
                    userRepository.insertUser(it)
                    withContext(dispatchers.main) {
                        insertUserLiveData.postValue(Event(DataState.Success(Unit)))
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
                minWeight = it.height - 106
                maxWeight = it.height - 102
            }

            "$minWeight kg - $maxWeight kg"
        } ?: ""
    }
}