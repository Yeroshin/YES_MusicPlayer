package com.yes.trackdialogfeature


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.not

open class BaseTestRobot {
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

}