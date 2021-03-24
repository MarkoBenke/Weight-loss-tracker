package com.marko.weightlosstracker.ui.main.settings

import com.marko.weightlosstracker.BuildConfig
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.utils.BaseTest
import com.marko.weightlosstracker.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class SettingsFragmentTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    override fun setup() {
        super.setup()
        hiltRule.inject()
        launchFragmentInHiltContainer<SettingsFragment> {}
    }

    @Test
    fun uiCheck() {
        isViewVisible(R.id.privacy)
        isViewVisible(R.id.terms)

        checkTextOnView(R.id.version, context.getString(R.string.version, BuildConfig.VERSION_NAME))
    }
}