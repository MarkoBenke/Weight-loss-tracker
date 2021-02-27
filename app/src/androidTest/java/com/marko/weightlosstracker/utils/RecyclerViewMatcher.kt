package com.marko.weightlosstracker.utils

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**
 * Matcher for recycler view item at specific position
 */
@Suppress("unused")
class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            internal var resources: Resources? = null
            internal var childView: View? = null

            override fun describeTo(description: Description) {
                var idDescription = Integer.toString(recyclerViewId)
                if (this.resources != null) {
                    idDescription = try {
                        this.resources!!.getResourceName(recyclerViewId)
                    } catch (exception: Resources.NotFoundException) {
                        String.format("%s (resource name not found)",
                            Integer.valueOf(recyclerViewId))
                    }
                }
                description.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View): Boolean {

                this.resources = view.resources

                if (childView == null) {
                    val recyclerView = view.rootView.findViewById<View>(recyclerViewId) as RecyclerView
                    if (recyclerView.id == recyclerViewId) {
                        childView = recyclerView.findViewHolderForAdapterPosition(position)?.itemView
                    } else {
                        return false
                    }
                }

                @Suppress("LiftReturnOrAssignment")
                if (targetViewId == -1) {
                    return view === childView
                } else {
                    val targetView = childView!!.findViewById<View>(targetViewId)
                    return view === targetView
                }
            }
        }
    }

    fun atNestedPositionOnView(position: Int, targetViewId: Int,
                               position2: Int, targetViewId2: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            internal var resources: Resources? = null
            internal var childView: View? = null

            override fun describeTo(description: Description) {
                var idDescription = Integer.toString(recyclerViewId)
                if (this.resources != null) {
                    idDescription = try {
                        this.resources!!.getResourceName(recyclerViewId)
                    } catch (exception: Resources.NotFoundException) {
                        String.format("%s (resource name not found)",
                            Integer.valueOf(recyclerViewId))
                    }
                }
                description.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View): Boolean {

                this.resources = view.resources

                if (childView == null) {
                    val recyclerView = view.rootView.findViewById<View>(recyclerViewId) as RecyclerView
                    if (recyclerView.id == recyclerViewId) {
                        childView = recyclerView.findViewHolderForAdapterPosition(position)?.itemView
                    } else {
                        return false
                    }
                }

                @Suppress("LiftReturnOrAssignment")
                if (targetViewId == -1) {
                    return view === childView
                } else {
                    val targetNestedView = childView!!.findViewById<View>(targetViewId) as RecyclerView
                    val childNestedView = targetNestedView.findViewHolderForAdapterPosition(position2)?.itemView
                    val targetView = childNestedView!!.findViewById<View>(targetViewId2)
                    return view === targetView
                }
            }
        }
    }
}