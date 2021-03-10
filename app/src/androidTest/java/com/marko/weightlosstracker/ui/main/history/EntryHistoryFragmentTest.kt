package com.marko.weightlosstracker.ui.main.history

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
class EntryHistoryFragmentTest : BaseTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    override fun setup() {
        super.setup()
        hiltRule.inject()
        launchFragmentInHiltContainer<EntryHistoryFragment> { }
    }

    @Test
    fun isFlagVisibleOnlyOnStartingItem() {
        isViewNotVisibleInRecyclerView(R.id.entriesRecView, R.id.flagIcon, FIRST_POSITION)
        isViewNotVisibleInRecyclerView(R.id.entriesRecView, R.id.flagIcon, SECOND_POSITION)
        isViewVisibleInRecyclerView(R.id.entriesRecView, R.id.flagIcon, THIRD_POSITION)
    }

    @Test
    fun checkWeightDifferenceColor() {
        checkTextColorInRecyclerView(
            R.id.entriesRecView,
            R.id.weightDifference,
            FIRST_POSITION,
            R.color.negativeTextColor
        )
        checkTextColorInRecyclerView(
            R.id.entriesRecView,
            R.id.weightDifference,
            SECOND_POSITION,
            R.color.positiveTextColor
        )
    }

    @Test
    fun checkItemViewsVisibility() {
        isViewVisibleInRecyclerView(R.id.entriesRecView, R.id.currentWeight, FIRST_POSITION)
        isViewVisibleInRecyclerView(R.id.entriesRecView, R.id.description, FIRST_POSITION)
        isViewVisibleInRecyclerView(R.id.entriesRecView, R.id.waistSize, FIRST_POSITION)
        isViewVisibleInRecyclerView(R.id.entriesRecView, R.id.date, FIRST_POSITION)
        isViewVisibleInRecyclerView(R.id.entriesRecView, R.id.weightDifference, FIRST_POSITION)

        isViewNotVisibleInRecyclerView(R.id.entriesRecView, R.id.description, THIRD_POSITION)
        isViewNotVisibleInRecyclerView(R.id.entriesRecView, R.id.waistSize, SECOND_POSITION)
    }

    @Test
    fun swipeToDeleteConfirm() {
        checkRecyclerViewItemCount(R.id.entriesRecView, 3)
        sleepMedium()
        swipeItemInRecyclerView(R.id.entriesRecView, FIRST_POSITION)
        sleepMedium()

        isTextDisplayedInView(R.id.dialogTitle, context.getString(R.string.delete_entry_dialog_title))
        isTextDisplayedInView(R.id.dialogDescription, context.getString(R.string.delete_entry_dialog_description))

        clickOnView(R.id.confirm)
        sleepShort()
        checkRecyclerViewItemCount(R.id.entriesRecView, 2)
    }

    @Test
    fun swipeToDeleteCancel() {
        checkRecyclerViewItemCount(R.id.entriesRecView, 3)
        sleepMedium()
        swipeItemInRecyclerView(R.id.entriesRecView, FIRST_POSITION)
        sleepMedium()

        isTextDisplayedInView(R.id.dialogTitle, context.getString(R.string.delete_entry_dialog_title))
        isTextDisplayedInView(R.id.dialogDescription, context.getString(R.string.delete_entry_dialog_description))

        clickOnView(R.id.cancel)
        sleepShort()
        checkRecyclerViewItemCount(R.id.entriesRecView, 3)
    }
}