package com.marko.weightlosstracker.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<BaseModelData> : ViewModel() {

    val modelLiveData: MutableLiveData<BaseModelData> = MutableLiveData()

    abstract fun fetchInitialData()
}