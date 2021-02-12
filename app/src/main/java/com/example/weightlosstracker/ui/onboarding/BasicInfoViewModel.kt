package com.example.weightlosstracker.ui.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasicInfoViewModel @Inject constructor() : ViewModel() {

    val validateLiveData = MutableLiveData<Event<DataState<Unit>>>()

    fun validate(height: String, age: String, currentWeight: String) {
        if (height.isEmpty() || age.isEmpty() || currentWeight.isEmpty()) {
            validateLiveData.postValue(Event(DataState.Error()))
        } else {
            validateLiveData.postValue(Event(DataState.Success(Unit)))
        }
    }
}