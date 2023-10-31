package com.yes.trackdialogfeature


import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.yes.core.data.entity.PlayListDataBaseTrackEntity
import com.yes.core.domain.repository.IPlayListDao
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher


open class BaseTestRobot() {
    fun view(resId: Int): ViewInteraction = onView(withId(resId))
    fun matchText(viewInteraction: ViewInteraction, text: String): ViewInteraction = viewInteraction
        .check(matches(withText(text)))

    // fun matchText(resId: Int, text: String): ViewInteraction = matchText(textView(resId), text)
    fun hasNoText(viewInteraction: ViewInteraction): ViewInteraction = viewInteraction
        .check(matches(withText("")))

    fun isDisplayed(viewInteraction: ViewInteraction): ViewInteraction = viewInteraction
        .check(matches(ViewMatchers.isDisplayed()))

    fun isNotDisplayed(viewInteraction: ViewInteraction): ViewInteraction = viewInteraction
        .check(matches(not(ViewMatchers.isDisplayed())))
fun isEnabled(viewInteraction: ViewInteraction): ViewInteraction = viewInteraction
    .check(matches(ViewMatchers.isEnabled()))
    fun isNotEnabled(viewInteraction: ViewInteraction): ViewInteraction = viewInteraction
        .check(matches(not(ViewMatchers.isEnabled())))
    fun matchRecyclerViewItemDescendantTextAtPosition(
        viewInteraction: ViewInteraction,
        position: Int,
        viewId: Int,
        text: String
    ): ViewInteraction = viewInteraction
        .perform(scrollToPosition<RecyclerView.ViewHolder>(position))
        .check(matches(atPosition(position, ViewMatchers.hasDescendant(withId(viewId)), text)))

    fun notMatchRecyclerViewItemDescendantTextAtPosition(
        viewInteraction: ViewInteraction,
        position: Int,
        viewId: Int,
        text: String
    ): ViewInteraction = viewInteraction
        .perform(scrollToPosition<RecyclerView.ViewHolder>(position))
        .check(matches(not(atPosition(position, ViewMatchers.hasDescendant(withId(viewId)), text))))

    private fun atPosition(
        position: Int,
        itemMatcher: Matcher<View>,
        expectedText: String?
    ): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder = view.findViewHolderForAdapterPosition(position) ?: return false
                val itemView = viewHolder.itemView
                var matchResult = itemMatcher.matches(itemView)

                if (expectedText != null) {
                    val textView = itemView.findViewById<TextView>(R.id.item_title)
                    val actualText = textView.text.toString()
                    matchResult = matchResult && actualText == expectedText
                }

                return matchResult
            }
        }
    }

    fun clickView(
        viewInteraction: ViewInteraction,
    ): ViewInteraction = viewInteraction
        .perform(
            click()
        )

    fun clickRecyclerViewItemView(
        viewInteraction: ViewInteraction,
        position: Int
    ): ViewInteraction = viewInteraction
        .perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                click()
            )
        )

    fun clickRecyclerViewItemViewChildView(
        viewInteraction: ViewInteraction,
        position: Int,
        childViewId: Int,
    ): ViewInteraction = viewInteraction
        .perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                clickChildViewWithId(childViewId)
            )
        )


    private fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id)
                v.performClick()
            }
        }
    }
    fun typeText(
        viewInteraction: ViewInteraction,
        text:String
    ): ViewInteraction = viewInteraction
        .perform(ViewActions.typeText(text), ViewActions.closeSoftKeyboard())

    fun readTracksFromPlaylistDB(
        playlistName: String,
        playListRepository: IPlayListDao
    ): List<PlayListDataBaseTrackEntity> {
        return playListRepository.getTracks(playlistName)
    }
}