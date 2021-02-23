package com.example.weightlosstracker.util

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

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

fun Float.roundUp(fraction: Int = 2): Float {
    val formatter: NumberFormat = NumberFormat.getInstance(Locale.US)
    formatter.isGroupingUsed = false
    formatter.maximumFractionDigits = fraction
    formatter.minimumFractionDigits = fraction
    formatter.roundingMode = RoundingMode.HALF_UP
    return java.lang.Float.valueOf(formatter.format(this))
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

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }