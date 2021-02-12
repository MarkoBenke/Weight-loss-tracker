package com.example.weightlosstracker.ui.onboarding

import android.os.Bundle
import com.example.weightlosstracker.domain.Gender
import com.example.weightlosstracker.domain.User
import com.example.weightlosstracker.utils.BaseTest
import com.example.weightlosstracker.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class BasicInfoFragmentTest: BaseTest() {

    val user = User(
        startWeight = 95f, currentWeight = 85f, targetWeight = 75f, startWaistSize = 102,
        startBmi = 30.2f, height = 173f, startDate = "30.01.2021", age = 30, gender = Gender.MALE,
        goalName = "Journey to 75kg"
    )

    @Test
    fun test() {
        val bundle = Bundle().apply {
            putParcelable(OnBoardingActivity.USER_KEY, user)
        }
        launchFragmentInHiltContainer<BasicInfoFragment>(
            fragmentArgs = bundle
        ) {}
    }
}