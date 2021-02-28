package com.marko.weightlosstracker.ui.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<BaseModelData> : ViewModel() {

    val modelLiveData: MutableLiveData<BaseModelData> = MutableLiveData()

    abstract fun fetchInitialData()
}