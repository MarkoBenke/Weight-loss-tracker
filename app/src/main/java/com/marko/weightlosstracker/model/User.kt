package com.marko.weightlosstracker.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
class User(
    var uuid: String = "",
    var username: String = "",
    var startWeight: Float = 0f,
    var currentWeight: Float = 0f,
    var targetWeight: Float = 0f,
    var startWaistSize: Int = 0,
    var startBmi: Float = 0f,
    var height: Float = 0f,
    var startDate: String = "",
    var age: Int = 0,
    var gender: Gender = Gender.MALE,
    var goalName: String = ""
) : Parcelable {

    fun getInitialWeightEntry(): WeightEntry {
        return WeightEntry(
            uuid = startDate.replace(".", ""),
            currentWeight = currentWeight,
            waistSize = startWaistSize,
            date = startDate,
            description = goalName
        )
    }
}