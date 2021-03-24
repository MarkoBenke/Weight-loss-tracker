package com.marko.weightlosstracker.ui.onboarding

class BasicInfoValidationModel(
    var username: String, var height: String, var age: String, var currentWeight: String
) {

    fun isValid(): Boolean {
        return username.isNotEmpty() && height.isNotEmpty() && age.isNotEmpty() && currentWeight.isNotEmpty()
    }
}