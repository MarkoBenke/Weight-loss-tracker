package com.marko.weightlosstracker.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class WeightEntry(
    var uuid: String,
    var currentWeight: Float,
    var waistSize: Int,
    val date: String,
    var description: String,
    var isInitialEntry: Boolean = false
) : Parcelable
