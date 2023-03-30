package com.yes.trackdialogfeature

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class TrackDialogTest {


    @Test
    fun showsRootCategoriesSuccess(){
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val n=appContext.packageName
        Assert.assertEquals("com.yes.trackdialogfeature.test", appContext.packageName)
       /* val scenario = launchFragment<TrackDialog>()
        scenario.moveToState(Lifecycle.State.STARTED)
        scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)
        }*/
    }
}