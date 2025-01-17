package com.marko.weightlosstracker.util

import androidx.annotation.Keep

@Keep
sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()

    data class Error(val message: String = "") : DataState<Nothing>()

    object Loading : DataState<Nothing>()
}
