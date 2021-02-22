package com.example.weightlosstracker.ui.main.history

import com.example.weightlosstracker.R
import com.example.weightlosstracker.utils.BaseTest
import com.example.weightlosstracker.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
class EntryHistoryFragmentTest : BaseTest() {

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
    fun swipeToDelete() {
        checkRecyclerViewItemCount(R.id.entriesRecView, 3)
        sleepLong()
        swipeItemInRecyclerView(R.id.entriesRecView, FIRST_POSITION)
        sleepMedium()
        checkRecyclerViewItemCount(R.id.entriesRecView, 2)
        checkSnackbarText(R.string.successfully_deleted_entry)
    }

    @Test
    fun swipeToDeleteAndUndo() {
        checkRecyclerViewItemCount(R.id.entriesRecView, 3)
        sleepLong()
        swipeItemInRecyclerView(R.id.entriesRecView, FIRST_POSITION)
        checkRecyclerViewItemCount(R.id.entriesRecView, 2)
        clickOnText(R.string.undo)
        sleepMedium()
        checkRecyclerViewItemCount(R.id.entriesRecView, 3)
    }
}