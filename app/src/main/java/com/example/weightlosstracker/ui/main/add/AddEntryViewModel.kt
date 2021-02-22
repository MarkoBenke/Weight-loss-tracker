package com.example.weightlosstracker.ui.main.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weightlosstracker.domain.WeightEntry
import com.example.weightlosstracker.repository.user.UserRepository
import com.example.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.example.weightlosstracker.util.BaseViewModel
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.DispatcherProvider
import com.example.weightlosstracker.util.Event
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

    val insertWeightLiveData = MutableLiveData<Event<DataState<Unit>>>()

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
            insertWeightLiveData.postValue(Event(DataState.Error()))
        } else {
            viewModelScope.launch(dispatcherProvider.io) {
                weightEntryRepository.insertWeight(weightEntry)
                insertWeightLiveData.postValue(Event(DataState.Success(Unit)))
            }
        }
    }
}