package com.example.weightlosstracker.util

import android.util.Log

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {

        if (hasBeenHandled) {
            Log.d("BENI", "getContentIfNotHandled: 1 $hasBeenHandled")
            return null
        } else {
            Log.d("BENI", "getContentIfNotHandled: 2 $hasBeenHandled")
            hasBeenHandled = true
            return content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}