package com.example.weightlosstracker.ui.onboarding

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.pressBack
import com.example.weightlosstracker.R
import com.example.weightlosstracker.utils.BaseTest
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class OnBoardingActivityTest : BaseTest() {

    override fun setup() {
        hiltRule.inject()
        super.setup()
    }

    @Test
    fun walkThroughOnBoarding() {
        val onBoardingActivity = ActivityScenario.launch(OnBoardingActivity::class.java)

        //screen 1
        isTextDisplayedInView(R.id.genderTitle, R.string.select_gender_title)
        clickOnView(R.id.next)

        //screen 2
        isTextDisplayedInView(R.id.weightDataText, R.string.weight_data_title)
        clickOnView(R.id.next)
        checkSnackbarText(R.string.mandatory_fields_error_message)

        fillBasicInfoData()
        clickOnView(R.id.next)

        //screen 3
        isViewVisible(R.id.idealWeight)
        clickOnView(R.id.submit)
        checkSnackbarText(R.string.target_weight_error_message)

        typeTextOnInputLayout(R.id.targetWeight, "70")
        clickOnView(R.id.submit)

        assert(onBoardingActivity.state.isAtLeast(Lifecycle.State.DESTROYED))
    }

    @Test
    fun onBoardingNavigation() {
        ActivityScenario.launch(OnBoardingActivity::class.java)

        //screen 1
        isViewVisible(R.id.genderTitle)
        clickOnView(R.id.next)

        //screen 2
        isViewVisible(R.id.weightDataText)
        fillBasicInfoData()
        clickOnView(R.id.next)

        //screen 3
        isViewVisible(R.id.idealWeight)

        pressBack()

        //screen 2
        isViewVisible(R.id.weightDataText)

        pressBack()

        //screen 1
        isViewVisible(R.id.genderTitle)
    }

    private fun fillBasicInfoData() {
        typeTextOnInputLayout(R.id.height, "175")
        typeTextOnInputLayout(R.id.age, "30")
        typeTextOnInputLayout(R.id.currentWeight, "85")
        typeTextOnInputLayout(R.id.waistSize, "88")
    }
}