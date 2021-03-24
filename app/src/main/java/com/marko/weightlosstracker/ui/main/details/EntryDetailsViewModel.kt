package com.marko.weightlosstracker.ui.main.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.marko.weightlosstracker.ui.core.DispatcherProvider
import com.marko.weightlosstracker.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryDetailsViewModel @Inject constructor(
    private val weightEntryRepository: WeightEntryRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _weightEntryAction = MutableLiveData<DataState<Unit>>()
    val weightEntryAction: LiveData<DataState<Unit>> = _weightEntryAction

    private val _validation = MutableLiveData<DataState<Unit>>()
    val validation: LiveData<DataState<Unit>> = _validation

    fun validate(weight: String) {
        if (weight.isEmpty()) _validation.postValue(DataState.Error())
        else _validation.postValue(DataState.Success(Unit))
    }

    fun delete(weightEntry: WeightEntry) {
        viewModelScope.launch(dispatchers.io) {
            weightEntryRepository.deleteWeightEntry(weightEntry).collect {
                _weightEntryAction.postValue(it)
            }
        }
    }

    fun update(weightEntry: WeightEntry) {
        viewModelScope.launch(dispatchers.io) {
            weightEntryRepository.updateWeightEntry(weightEntry).collect {
                _weightEntryAction.postValue(it)
            }
        }
    }
}