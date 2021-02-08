package com.example.weightlosstracker.ui.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasicInfoViewModel @Inject constructor() : ViewModel() {

    val validateLiveData = MutableLiveData<Boolean>()

    fun validate(height: String, age: String, currentWeight: String) {
        validateLiveData.postValue(
            height.isNotEmpty() && age.isNotEmpty() && currentWeight.isNotEmpty()
        )
    }
}