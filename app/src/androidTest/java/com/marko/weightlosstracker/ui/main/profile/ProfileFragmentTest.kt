package com.marko.weightlosstracker.ui.main.profile

import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.utils.BaseTest
import com.marko.weightlosstracker.utils.DataGenerator
import com.marko.weightlosstracker.utils.DataGenerator.largeName
import com.marko.weightlosstracker.utils.DataGenerator.largeNumber
import com.marko.weightlosstracker.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class ProfileFragmentTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    override fun setup() {
        super.setup()
        hiltRule.inject()
        launchFragmentInHiltContainer<ProfileFragment> {}
    }

    @Test
    fun uiCheck() {
        val user = DataGenerator.user
        checkTextOnView(R.id.usernameEditText, user.username)
        checkTextOnView(R.id.ageEditText, user.age.toString())
        checkTextOnView(R.id.targetWeightEditText, user.targetWeight.toString())

        checkHintTextOnInputField(R.id.username, context.getString(R.string.username))
        checkHintTextOnInputField(R.id.age, context.getString(R.string.age))
        checkHintTextOnInputField(R.id.targetWeight, context.getString(R.string.target_weight))

        isViewVisible(R.id.submit)
    }

    @Test
    fun validationInvalidUsername() {
        replaceTextOnInputLayout(R.id.username, "")

        clickOnView(R.id.submit)

        checkSnackbarText(R.string.mandatory_fields_error_message)
    }

    @Test
    fun validationInvalidAge() {
        replaceTextOnInputLayout(R.id.age, "")

        clickOnView(R.id.submit)

        checkSnackbarText(R.string.mandatory_fields_error_message)
    }

    @Test
    fun validationInvalidTargetWeight() {
        replaceTextOnInputLayout(R.id.age, "")

        clickOnView(R.id.submit)

        checkSnackbarText(R.string.mandatory_fields_error_message)
    }

    @Test
    fun fieldsMaxInputLength() {
        replaceTextOnInputLayout(R.id.username, largeName)
        checkMaxInputLength(
            R.id.usernameEditText,
            context.resources.getInteger(R.integer.name_entry_max_length)
        )

        replaceTextOnInputLayout(R.id.age, largeNumber)
        checkMaxInputLength(
            R.id.ageEditText,
            context.resources.getInteger(R.integer.age_entry_max_length)
        )

        replaceTextOnInputLayout(R.id.targetWeight, largeNumber)
        checkMaxInputLength(
            R.id.targetWeightEditText,
            context.resources.getInteger(R.integer.weight_entry_max_length)
        )
    }
}