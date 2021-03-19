package com.marko.weightlosstracker.ui.main.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.marko.weightlosstracker.ui.core.BaseViewModel
import com.marko.weightlosstracker.ui.core.DispatcherProvider
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEntryViewModel @Inject constructor(
    private val weightEntryRepository: WeightEntryRepository,
    private val userRepository: UserRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<Long>() {

    private val _validationLiveData = MutableLiveData<Event<DataState<String>>>()
    val validationLiveData: LiveData<Event<DataState<String>>> = _validationLiveData

    private val _insertWeightLiveData = MutableLiveData<DataState<Unit>>()
    val insertWeightLiveData: LiveData<DataState<Unit>> = _insertWeightLiveData

    override fun fetchInitialData() {
        getStartDate()
    }

    private fun getStartDate() {
        viewModelScope.launch(dispatcherProvider.io) {
            userRepository.getUsersStartDate().collect { startDateInMillis ->
                modelLiveData.postValue(startDateInMillis)
            }
        }
    }

    fun validate(newWeight: String) {
        if (newWeight.isEmpty()) {
            _validationLiveData.postValue(Event(DataState.Error()))
        } else {
            _validationLiveData.postValue(Event(DataState.Success(newWeight)))
        }
    }

    fun insertWeightEntry(weightEntry: WeightEntry) {
        viewModelScope.launch(dispatcherProvider.io) {
            weightEntryRepository.insertWeight(weightEntry).collect {
                _insertWeightLiveData.postValue(it)
            }
        }
    }
}