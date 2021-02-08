package com.example.weightlosstracker.utils

import android.app.Activity
import android.view.View
import android.widget.EditText
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.*
import org.hamcrest.CoreMatchers.*
import org.hamcrest.core.StringContains

abstract class BaseUiTest  {

    fun viewPagerSwipeLeft(viewId: Int) {
        onView(withId(viewId)).perform(swipeLeft())
    }

    fun viewPagerSwipeRight(viewId: Int) {
        onView(withId(viewId)).perform(swipeRight())
    }

    fun checkTextOnRecyclerViewItem(viewId: Int, itemId: Int, position: Int, text: String) {
        onView(withRecyclerView(viewId).atPositionOnView(position, itemId))
            .check(matches(withText(containsString(text))))
    }

    fun isViewDisplayedInRecyclerView(viewId: Int, itemId: Int, position: Int) {
        onView(withRecyclerView(viewId).atPositionOnView(position, itemId))
            .check(matches(isDisplayed()))
    }

    fun isViewNotDisplayedInRecyclerView(viewId: Int, itemId: Int, position: Int) {
        onView(withRecyclerView(viewId).atPositionOnView(position, itemId))
            .check(matches(not(isDisplayed())))
    }


    fun checkErrorTextOnInputField(viewId: Int, errorText: String) {
        onView(withId(viewId)).check(
            matches(
                hasTextInputLayoutErrorText(
                    errorText
                )
            )
        )
    }

    fun checkToastMessage(activity: Activity, text: String) {
        onView(withText(text)).inRoot(withDecorView(not(activity.window.decorView)))
            .check(matches(isDisplayed()))
    }

    fun checkRecyclerViewItemCount(viewId: Int, expectedCount: Int) {
        onView(withId(viewId)).check(
            RecyclerViewItemCountAssertion(
                expectedCount
            )
        )
    }

    fun typeTextOnInputLayout(viewId: Int, text: String) {
        onView(
            allOf(
                isDescendantOfA(withId(viewId)),
                isAssignableFrom(EditText::class.java)
            )
        ).perform(typeText(text))
        closeSoftKeyboard()
    }

    /**
     * Check is view visible
     * @id id of view
     */
    protected fun isViewVisible(id: Int) = onView(withId(id)).check(matches(isDisplayed()))

    /**
     * Check if view is not visible
     * @id id of view
     */
    protected fun isViewNotVisible(id: Int) = onView(withId(id)).check(matches(not(isDisplayed())))

    /**
     * Check is view focused
     * @id id of view
     */
    protected fun isViewFocused(id: Int) = onView(withId(id)).check(matches(hasFocus()))

    /**
     * Check if view is checked
     * @id id of view
     */
    protected fun isViewChecked(id: Int) = onView(withId(id)).check(matches(isChecked()))

    /**
     * Check if view is not checked
     * @id id of view
     */
    protected fun isViewNotChecked(id: Int) = onView(withId(id)).check(matches(not(isSelected())))

    /**
     * Check is text visible
     * @id string resource id
     */
    protected fun isTextVisible(resourceId: Int) =
        onView(withText(resourceId)).check(matches(isDisplayed()))

    /**
     * Check is text visible
     * @string string
     */
    protected fun isTextVisible(string: String) =
        onView(withText(string)).check(matches(isDisplayed()))

    /**
     * Check is text not visible
     * @id string resource id
     */
    protected fun isTextNotVisible(resourceId: Int) =
        onView(withText(resourceId)).check(doesNotExist())

    /**
     * Check is text not visible
     * @string string
     */
    protected fun isTextNotVisible(string: String) = onView(withText(string)).check(doesNotExist())

    /**
     * Click on view
     * @id id of view
     */
    protected fun clickOnView(id: Int) = onView(withId(id)).perform(click())

    /**
     * Click on text
     * @id string resource id
     */
    protected fun clickOnText(resourceId: Int) = onView(withText(resourceId)).perform(click())

    /**
     * Click on text
     * @id string resource id
     */
    protected fun clickOnText(string: String) = onView(withText(string)).perform(click())

    /**
     * Updates text attribute of a view
     * @viewId view id
     * @text text to be replaced
     */
    protected fun replaceText(viewId: Int, text: String) {
        onView(withId(viewId)).perform(
            scrollTo(), click(),
            replaceText(text)
        )
        closeSoftKeyboard()
    }

    /**
     * Check max characters in EditText
     * @maxChars Max chars for given view
     */
    fun checkMaxInputLengthInOctopusEditText(maxChars: Int): TypeSafeMatcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description?) {
                description?.appendText(description.toString())
            }

            override fun matchesSafely(view: View?): Boolean {
                return (view as EditText).text!!.count() == maxChars
            }
        }
    }

    /**
     * Returns an action that scrolls to the view
     * @viewId view resource id
     */
    protected fun scrollToView(viewId: Int) {
        onView(withId(viewId)).perform(scrollTo())
    }

    /**
     * Returns an action that scrolls to the view and checks if its visible
     * @viewId view resource id
     */
    protected fun scrollToViewAndCheckIfVisible(viewId: Int) {
        scrollToView(viewId)
        isViewVisible(viewId)
    }

    /**
     * Returns an action that scrolls to the view and clicks on it
     * @viewId view resource id
     */
    protected fun scrollToViewAndClickOnIt(viewId: Int) {
        onView(withId(viewId)).perform(scrollTo(), click())
    }

    /**
     * Check if view is enabled
     * @viewId view id
     */
    fun isButtonEnabled(viewId: Int) = onView(withId(viewId)).check(matches(isEnabled()))

    fun scrollToAndReplaceText(viewId: Int, text: String) =
        onView(withId(viewId)).perform(scrollTo(), replaceText(text))


    fun checkTextOnView(viewId: Int, text: String) =
        onView(withId(viewId)).check(matches(withText(text)))


    fun checkTextOnView(viewId: Int, resId: Int) =
        onView(withId(viewId)).check(matches(withText(resId)))


    fun scrollAndCheckIsButtonDisabled(viewId: Int) =
        onView(withId(viewId)).perform(scrollTo()).check(matches(not(isEnabled())))

    fun scrollAndCheckIsButtonEnabled(viewId: Int) =
        onView(withId(viewId)).perform(scrollTo()).check(matches(isEnabled()))

    /**
     * Check is text displayed in view
     * @id view id
     * @text text inside view
     */
    protected fun isTextDisplayedInView(id: Int, text: String) =
        onView(withId(id)).check(matches(withText(text)))

    /**
     * Check is text contained in view
     * @id view id
     * @text text inside view
     */
    protected fun isTextContainedInView(id: Int, text: String) =
        onView(withId(id)).check(matches(withText(StringContains.containsString(text))))


    /**
     * Check text color of a view
     * @viewId view if
     * @colorRes resource color id
     */
    protected fun isTextInViewMatchedWithColor(viewId: Int, colorRes: Int) =
        onView(withId(viewId)).check(matches(hasTextColor(colorRes)))


    /**
     * Check is checkbox checked
     * @viewId checkbox resource id
     */
    protected fun isCheckBoxChecked(viewId: Int) =
        onView(withId(viewId)).check(matches(isChecked()))


    /**
     * Check is checkbox unchecked
     * @viewId checkbox resource id
     */
    protected fun isCheckBoxUnchecked(viewId: Int) =
        onView(withId(viewId)).check(matches(Matchers.not(isChecked())))


    /**
     * Scroll to next button and click on it using nestedScroll
     * @id resource id
     */
    fun scrollToButtonAndClickOnIt(id: Int) {
        onView(withId(id)).perform(scrollTo())
        onView(withId(id)).perform(click())
    }

    fun replaceTextOnInputLayout(viewId: Int, text: String) {
        onView(
            CoreMatchers.allOf(
                isDescendantOfA(withId(viewId)),
                isAssignableFrom(EditText::class.java)
            )
        ).perform(typeText(text))
    }

    protected fun clickOnRecyclerViewItem(
        recyclerViewId: Int, recyclerViewItemId: Int, position: Int
    ) {
        onView(
            withRecyclerView(recyclerViewId)
                .atPositionOnView(position, recyclerViewItemId)
        ).perform(click())
    }

    /**
     * Returns RecyclerViewMatcher with provided id
     * @recyclerViewId recycler resource id
     */
    fun withRecyclerView(recyclerViewId: Int): RecyclerViewMatcher {
        return RecyclerViewMatcher(recyclerViewId)
    }

    private fun hasTextInputLayoutErrorText(errorText: String): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {

            override fun describeTo(description: Description?) {}

            override fun matchesSafely(item: View?): Boolean {
                if (item is TextInputLayout) {
                    return item.error?.equals(errorText)!!
                }

                return false
            }
        }
    }

    companion object {
        const val FIRST_POSITION = 0
        const val SECOND_POSITION = 1
    }
}