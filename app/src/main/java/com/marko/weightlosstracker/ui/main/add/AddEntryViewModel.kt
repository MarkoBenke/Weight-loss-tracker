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

    private val _insertWeightLiveData = MutableLiveData<Event<DataState<Unit>>>()
    val insertWeightLiveData: LiveData<Event<DataState<Unit>>> = _insertWeightLiveData

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

    fun insertNewEntry(newWeight: String, weightEntry: WeightEntry) {
        if (newWeight.isEmpty()) {
            _insertWeightLiveData.postValue(Event(DataState.Error()))
        } else {
            viewModelScope.launch(dispatcherProvider.io) {
                weightEntryRepository.insertWeight(weightEntry)
                _insertWeightLiveData.postValue(Event(DataState.Success(Unit)))
            }
        }
    }
}