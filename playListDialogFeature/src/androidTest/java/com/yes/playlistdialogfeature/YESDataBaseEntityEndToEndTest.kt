package com.yes.playlistdialogfeature

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.yes.core.util.EspressoIdlingResource
import com.yes.playlistdialogfeature.di.components.DaggerTestPlayListDialogComponent
import com.yes.playlistdialogfeature.di.components.TestPlayListDialogComponent
import com.yes.playlistdialogfeature.di.module.TestAppModule
import com.yes.playlistdialogfeature.di.module.TestPlayListDialogModule
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import org.junit.After
import org.junit.Before
import org.junit.Test

class YESDataBaseEntityEndToEndTest {
    private lateinit var di: TestPlayListDialogComponent
    private lateinit var scenario: FragmentScenario<PlayListDialog>
    class MockFragmentFactoryImpl(
        private val dep: PlayListDialog.Dependency
    ) : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return when (loadFragmentClass(classLoader, className)) {
                PlayListDialog::class.java -> PlayListDialog(dep)
                else -> super.instantiate(classLoader, className)
            }
        }
    }
    @Before
    fun setUp() {
       // mediaFileFixtures.writeNonExistFiles()

        di = DaggerTestPlayListDialogComponent.builder()
            .testPlayListDialogModule(TestPlayListDialogModule())
            .testAppModule(TestAppModule(ApplicationProvider.getApplicationContext()))
            .build()
        val factory = di.getViewModelFactory()
      //  dataBase = di.getDataBase()
       // settings = di.getSettings()
        val dependency = PlayListDialog.Dependency(
            factory
        )
        val playListDialogFactory = MockFragmentFactoryImpl(
            dependency
        )
        scenario = launchFragment(
            factory = playListDialogFactory
        )

    }
    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testLoadingItemsOnInit(){
        scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)

        }
        Espresso.onView(ViewMatchers.withId(com.yes.coreui.R.id.dialogTitle))
            .check(ViewAssertions.matches(ViewMatchers.withText("")))
    }
}