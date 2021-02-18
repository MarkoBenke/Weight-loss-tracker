package com.example.weightlosstracker.ui.main.add

import com.example.weightlosstracker.R
import com.example.weightlosstracker.util.getCurrentDate
import com.example.weightlosstracker.utils.BaseTest
import com.example.weightlosstracker.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class AddEntryFragmentTest: BaseTest() {

    private val largeNumber = "123456"

    override fun setup() {
        super.setup()
        hiltRule.inject()
        launchFragmentInHiltContainer<AddEntryFragment> {}
    }

    @Test
    fun addEntryUiCheck() {
        checkHintTextOnInputField(R.id.newWeight, context.getString(R.string.goal_details_current_weight))
        checkHintTextOnInputField(R.id.waistSize, context.getString(R.string.goal_details_waist_size))
        checkHintTextOnInputField(R.id.description, context.getString(R.string.goal_details_description))

        checkHelperTextOnInputField(R.id.newWeight, context.getString(R.string.mandatory_field))
        checkHelperTextOnInputField(R.id.waistSize, context.getString(R.string.optional_field))
        checkHelperTextOnInputField(R.id.description, context.getString(R.string.optional_field))

        isViewVisible(R.id.setDate)
        isTextDisplayedInView(R.id.setDateText, getCurrentDate())
        isViewVisible(R.id.submitBtn)
    }

    @Test
    fun checkCalendarUpdates() {
        isTextDisplayedInView(R.id.setDateText, getCurrentDate())

        clickOnView(R.id.setDate)
        selectDateInCalendar(5, 10,2020)
        clickOnCalendarOk()

        isTextDisplayedInView(R.id.setDateText, "05.10.2020")
    }

    @Test
    fun basicInfoFieldsMaxInputLength() {
        val maxDescriptionLength = context.resources.getInteger(R.integer.description_max_length)
        val description = buildString {
            for (i in 1..maxDescriptionLength + 1) {
                append(1)
            }
        }

        typeTextOnInputLayout(R.id.newWeight, largeNumber)
        checkMaxInputLength(
            R.id.newWeightEditText,
            context.resources.getInteger(R.integer.weight_entry_max_length)
        )

        typeTextOnInputLayout(R.id.waistSize, largeNumber)
        checkMaxInputLength(
            R.id.waistSizeEditText,
            context.resources.getInteger(R.integer.waist_size_max_length)
        )

        typeTextOnInputLayout(R.id.description, description)
        checkMaxInputLength(
            R.id.descriptionEditText,
            context.resources.getInteger(R.integer.description_max_length)
        )
    }

    @Test
    fun addNewEntryValidationFailed() {
        typeTextOnInputLayout(R.id.waistSize, largeNumber)
        typeTextOnInputLayout(R.id.description, largeNumber)

        clickOnView(R.id.submitBtn)
        checkSnackbarText(R.string.current_weight_error_message)
    }

    @Test
    fun addNewEntryValidationSuccess() {
        typeTextOnInputLayout(R.id.newWeight, largeNumber)
        typeTextOnInputLayout(R.id.waistSize, largeNumber)
        typeTextOnInputLayout(R.id.description, largeNumber)

        clickOnView(R.id.submitBtn)
        checkSnackbarText(R.string.entry_success)
    }
}