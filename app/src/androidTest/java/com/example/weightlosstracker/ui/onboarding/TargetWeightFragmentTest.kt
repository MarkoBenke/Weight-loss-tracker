package com.example.weightlosstracker.ui.onboarding

import android.os.Bundle
import com.example.weightlosstracker.R
import com.example.weightlosstracker.utils.BaseTest
import com.example.weightlosstracker.utils.DataGenerator
import com.example.weightlosstracker.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class TargetWeightFragmentTest: BaseTest() {

    override fun setup() {
        super.setup()
        hiltRule.inject()
        val bundle = Bundle().apply {
            putParcelable(OnBoardingActivity.USER_KEY, DataGenerator.user)
        }
        launchFragmentInHiltContainer<TargetWeightFragment>(
            fragmentArgs = bundle
        ) {}
    }

    @Test
    fun targetWeightUiCheck() {
        isViewVisible(R.id.idealWeight)

        checkHintTextOnInputField(R.id.targetWeight, context.getString(R.string.desired_weight))

        checkHelperTextOnInputField(R.id.targetWeight, context.getString(R.string.mandatory_field))

        isTextDisplayedInView(R.id.submit, R.string.submit_button_text)
    }

    @Test
    fun targetWeightFieldMaxInputLength() {
        val largeNumber = "123456"
        typeTextOnInputLayout(R.id.targetWeight, largeNumber)
        checkMaxInputLength(
            R.id.targetWeightEditText,
            context.resources.getInteger(R.integer.weight_entry_max_length)
        )
    }
}