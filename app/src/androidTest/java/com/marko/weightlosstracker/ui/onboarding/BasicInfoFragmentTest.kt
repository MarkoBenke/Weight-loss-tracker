package com.marko.weightlosstracker.ui.onboarding

import android.os.Bundle
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.util.getCurrentDate
import com.marko.weightlosstracker.utils.BaseTest
import com.marko.weightlosstracker.utils.DataGenerator
import com.marko.weightlosstracker.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class BasicInfoFragmentTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    override fun setup() {
        super.setup()
        hiltRule.inject()
        val bundle = Bundle().apply {
            putParcelable(OnBoardingActivity.USER_KEY, DataGenerator.user)
        }
        launchFragmentInHiltContainer<BasicInfoFragment>(
            fragmentArgs = bundle
        ) {}
    }

    @Test
    fun basicInfoUiCheck() {
        isTextDisplayedInView(R.id.weightDataText, context.getString(R.string.weight_data_title))

        checkHintTextOnInputField(R.id.height, context.getString(R.string.height))
        checkHintTextOnInputField(R.id.age, context.getString(R.string.age))
        checkHintTextOnInputField(R.id.currentWeight, context.getString(R.string.goal_details_current_weight))
        checkHintTextOnInputField(R.id.waistSize, context.getString(R.string.goal_details_waist_size))

        checkHelperTextOnInputField(R.id.height, context.getString(R.string.mandatory_field))
        checkHelperTextOnInputField(R.id.age, context.getString(R.string.mandatory_field))
        checkHelperTextOnInputField(R.id.currentWeight, context.getString(R.string.mandatory_field))
        checkHelperTextOnInputField(R.id.waistSize, context.getString(R.string.optional_field))

        isViewVisible(R.id.setDate)
        isTextDisplayedInView(R.id.setDateText, getCurrentDate())
        isTextDisplayedInView(R.id.next, R.string.next_button_text)
    }

    @Test
    fun checkCalendarUpdatesFromButton() {
        isTextDisplayedInView(R.id.setDateText, getCurrentDate())

        clickOnView(R.id.setDate)
        selectDateInCalendar(5, 10,2020)
        clickOnCalendarOk()

        isTextDisplayedInView(R.id.setDateText, "05.10.2020")
    }

    @Test
    fun checkCalendarUpdatesFromText() {
        isTextDisplayedInView(R.id.setDateText, getCurrentDate())

        clickOnView(R.id.setDateText)
        selectDateInCalendar(5, 10,2020)
        clickOnCalendarOk()

        isTextDisplayedInView(R.id.setDateText, "05.10.2020")
    }

    @Test
    fun basicInfoFieldsMaxInputLength() {
        val largeNumber = "123456"
        typeTextOnInputLayout(R.id.height, largeNumber)
        checkMaxInputLength(
            R.id.heightEditText,
            context.resources.getInteger(R.integer.height_entry_max_length)
        )

        typeTextOnInputLayout(R.id.age, largeNumber)
        checkMaxInputLength(
            R.id.ageEditText,
            context.resources.getInteger(R.integer.age_entry_max_length)
        )

        typeTextOnInputLayout(R.id.currentWeight, largeNumber)
        checkMaxInputLength(
            R.id.currentWeightEditText,
            context.resources.getInteger(R.integer.weight_entry_max_length)
        )

        typeTextOnInputLayout(R.id.waistSize, largeNumber)
        checkMaxInputLength(
            R.id.waistSizeEditText,
            context.resources.getInteger(R.integer.waist_size_max_length)
        )
    }
}