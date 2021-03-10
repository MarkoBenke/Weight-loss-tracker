package com.marko.weightlosstracker.ui.main.details

import android.os.Bundle
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.ui.main.MainActivity
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
class EntryDetailsFragmentTest: BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    override fun setup() {
        super.setup()
        hiltRule.inject()
    }

    @Test
    fun checkUi() {
        launchFragment()
        isViewVisible(R.id.submit)
        isViewVisible(R.id.delete)

        checkHelperTextOnInputField(R.id.newWeight, context.getString(R.string.mandatory_field))
        checkHelperTextOnInputField(R.id.waistSize, context.getString(R.string.optional_field))
        checkHelperTextOnInputField(R.id.description, context.getString(R.string.optional_field))

        isTextDisplayedInView(R.id.newWeightEditText, DataGenerator.weightEntry.currentWeight.toString())
        isTextDisplayedInView(R.id.waistSizeEditText, DataGenerator.weightEntry.waistSize.toString())
        isTextDisplayedInView(R.id.descriptionEditText, DataGenerator.weightEntry.description)
    }

    @Test
    fun updateWeightEntryValidationError() {
        launchFragment()
        replaceTextOnInputLayout(R.id.newWeight, "")
        clickOnView(R.id.submit)

        checkErrorTextOnInputField(R.id.newWeight, context.getString(R.string.mandatory_field))

        typeTextOnInputLayout(R.id.newWeight, "95")
        checkErrorTextOnInputField(R.id.newWeight, "")
    }

    @Test
    fun initialEntryDeleteDisabled() {
        val weightEntry = DataGenerator.weightEntry
        weightEntry.isInitialEntry = true
        launchFragment(weightEntry)

        isButtonDisabled(R.id.delete)
    }

    private fun launchFragment(weightEntry: WeightEntry = DataGenerator.weightEntry) {
        val bundle = Bundle().apply {
            putParcelable(MainActivity.WEIGHT_ENTRY_KEY, weightEntry)
        }
        launchFragmentInHiltContainer<EntryDetailsFragment>(
            fragmentArgs = bundle
        ) {}
    }
}