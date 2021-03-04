package com.marko.weightlosstracker.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasicInfoViewModel @Inject constructor() : ViewModel() {

    private val _validateLiveData = MutableLiveData<Event<DataState<Unit>>>()
    val validateLiveData: LiveData<Event<DataState<Unit>>> = _validateLiveData

    fun validate(height: String, age: String, currentWeight: String) {
        if (height.isEmpty() || age.isEmpty() || currentWeight.isEmpty()) {
            _validateLiveData.postValue(Event(DataState.Error()))
        } else {
            _validateLiveData.postValue(Event(DataState.Success(Unit)))
        }
    }
}