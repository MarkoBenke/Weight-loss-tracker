package com.example.weightlosstracker.ui.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weightlosstracker.domain.Gender
import com.example.weightlosstracker.domain.User
import com.example.weightlosstracker.repository.user.UserRepository
import com.example.weightlosstracker.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TargetWeightViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    val validateLiveData = MutableLiveData<Boolean>()
    val insertUserLiveData = MutableLiveData<Boolean>()

    fun validate(targetWeight: String) {
        validateLiveData.postValue(targetWeight.isNotEmpty())
    }

    fun insertUserToDb(user: User?) {
        user?.let {
            viewModelScope.launch(dispatchers.io) {
                userRepository.insertUser(it)
                withContext(dispatchers.main) {
                    insertUserLiveData.postValue(true)
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