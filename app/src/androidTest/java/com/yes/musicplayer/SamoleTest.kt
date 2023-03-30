package com.yes.musicplayer

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
class SamoleTest {
    @Test
    fun launchFragmentAndVerifyUI() {
        // use launchInContainer to launch the fragment with UI
      /*  launchFragmentInContainer<SampleFragment>()

        // now use espresso to look for the fragment's text view and verify it is displayed
        onView(withId(R.id.textView)).check(matches(withText("I am a fragment")));*/
    }
}