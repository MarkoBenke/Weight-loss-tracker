package com.example.weightlosstracker.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout

private fun Context.hideKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun String.makeShort(length: Int = 5): String {
    return if (this.length > length) {
        this.substring(0, length)
    } else this
}

fun Float.shortToString(): String {
    return if (this.toString().length > 5) {
        this.toString().substring(0, 5)
    } else this.toString()
}

fun Float.short(): Float {
    return if (this.toString().length > 5) {
        this.toString().substring(0, 5).toFloat()
    } else this
}