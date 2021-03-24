package com.marko.weightlosstracker.ui.onboarding

import androidx.lifecycle.viewModelScope
import com.marko.weightlosstracker.model.Gender
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.ui.core.BaseViewModel
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
) : BaseViewModel<Event<DataState<Unit>>>() {

    fun createUser(targetWeight: String, user: User?) {
        if (targetWeight.isEmpty()) {
            modelLiveData.postValue(Event(DataState.Error()))
        } else {
            user?.let {
                viewModelScope.launch(dispatchers.io) {
                    userRepository.insertUser(it).collect {
                        modelLiveData.postValue(Event(it))
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

    override fun fetchInitialData() {
        /*NO-OP */
    }
}