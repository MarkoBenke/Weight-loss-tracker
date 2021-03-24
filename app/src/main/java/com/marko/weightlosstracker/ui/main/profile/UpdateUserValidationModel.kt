package com.marko.weightlosstracker.ui.main.profile

class UpdateUserValidationModel(
    var username: String, var age: String, var targetWeight: String
) {

    fun isValid(): Boolean {
        return username.isNotEmpty() && age.isNotEmpty() && targetWeight.isNotEmpty()
    }
}