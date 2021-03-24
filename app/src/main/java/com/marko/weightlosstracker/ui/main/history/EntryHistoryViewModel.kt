package com.marko.weightlosstracker.ui.main.history

import androidx.lifecycle.viewModelScope
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.marko.weightlosstracker.ui.core.BaseViewModel
import com.marko.weightlosstracker.ui.core.DispatcherProvider
import com.marko.weightlosstracker.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryHistoryViewModel @Inject constructor(
    private val weightEntryRepository: WeightEntryRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<DataState<List<WeightEntry>>>() {

    override fun fetchInitialData() {
        getAllWeightEntries()
    }

    fun deleteEntry(weightEntry: WeightEntry) {
        viewModelScope.launch(dispatcherProvider.io) {
            weightEntryRepository.deleteWeightEntryFromList(weightEntry).collect {
                modelLiveData.postValue(it)
            }
        }
    }

    private fun getAllWeightEntries() {
        viewModelScope.launch(dispatcherProvider.io) {
            weightEntryRepository.getLocalEntries().collect {
                modelLiveData.postValue(it)
            }
        }
    }
}