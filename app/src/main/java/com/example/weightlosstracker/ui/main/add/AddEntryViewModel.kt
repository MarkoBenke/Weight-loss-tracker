package com.example.weightlosstracker.ui.main.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weightlosstracker.domain.WeightEntry
import com.example.weightlosstracker.repository.user.UserRepository
import com.example.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.example.weightlosstracker.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEntryViewModel @Inject constructor(
    private val weightEntryRepository: WeightEntryRepository,
    private val userRepository: UserRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val validateLiveData = MutableLiveData<Boolean>()
    val insertWeightLiveData = MutableLiveData<Boolean>()
    val startDateLiveData = MutableLiveData<Long>()

    init {
        getStartDate()
    }

    private fun getStartDate() {
        viewModelScope.launch(dispatcherProvider.io) {
            userRepository.getUsersStartDate().collect { startDateInMillis ->
                startDateLiveData.postValue(startDateInMillis)
            }
        }
    }

    fun validate(currentWeight: String) {
        validateLiveData.postValue(currentWeight.isNotEmpty())
    }

    fun insertNewEntry(weightEntry: WeightEntry) {
        viewModelScope.launch(dispatcherProvider.io) {
            weightEntryRepository.insertWeight(weightEntry)
            insertWeightLiveData.postValue(true)
        }
    }
}