package com.marko.weightlosstracker.ui.core

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class ConnectivityManager @Inject constructor(context: Context) {

    private val connectionLiveData = ConnectionLiveData(context)

    // observe this in ui
    val isNetworkAvailable = MutableLiveData(false)

    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner){
        connectionLiveData.observe(lifecycleOwner, { isConnected ->
            isConnected?.let { isNetworkAvailable.value = it }
        })
    }

    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner){
        connectionLiveData.removeObservers(lifecycleOwner)
    }
}