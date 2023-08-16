package com.yes.trackdialogfeature.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import com.yes.trackdialogfeature.di.components.DaggerTestTrackDialogComponent
import com.yes.trackdialogfeature.di.module.TestAppModule
import com.yes.trackdialogfeature.di.module.TestTrackDialogModule
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import com.yes.trackdialogfeature.trackDialog
import com.yes.trackdialogfeature.util.EspressoIdlingResource
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class TrackDialogEndToEndTest {


    class MockFragmentFactoryImpl(
        private val dep: TrackDialog.TrackDialogDependency
    ) : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return when (loadFragmentClass(classLoader, className)) {
                TrackDialog::class.java -> TrackDialog(dep)
                else -> super.instantiate(classLoader, className)
            }
        }
    }
  /*  class MyActivity : AppCompatActivity() {


        private val factory=DaggerTestTrackDialogComponent.builder()
            .testTrackDialogModule(TestTrackDialogModule())
             .testAppModule(TestAppModule(this))
            .build()
            .getViewModelFactory()



        val dependency = TrackDialog.TrackDialogDependency(
            factory
        )
        val trackDialogFactory = MockFragmentFactoryImpl(
            dependency
        )
        //  MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        val scenario:FragmentScenario<TrackDialog> = launchFragment(
            factory = trackDialogFactory
        )
      /*  scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)
        }*/
    }*/
   /* class TestContainerActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
          //  setContentView(R.layout.activity_test_container)
        }
    }*/
    private lateinit var scenario:FragmentScenario<TrackDialog>
    @Before
    fun setUp() {
//////////////////////////////
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        /////////////////////////
       // val activity=MyActivity()
        val factory = DaggerTestTrackDialogComponent.builder()
            .testTrackDialogModule(TestTrackDialogModule())
            .testAppModule(TestAppModule(ApplicationProvider.getApplicationContext()))
            .build()
            .getViewModelFactory()
        //private val factory = MockViewModelFactory(viewModel)
        val dependency = TrackDialog.TrackDialogDependency(
            factory
        )
        val trackDialogFactory = MockFragmentFactoryImpl(
            dependency
        )

        //  MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        scenario = launchFragment(
            factory = trackDialogFactory
        )
       /* scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)
        }*/
    }
    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
    @Test
    fun onInitShowsTrackDialogStateIdle(){

        scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)

        }
        trackDialog {
            matchTitleHasNoText()
            matchProgressBarDisplayed()
            matchDisableViewDisplayed()
        }
       /* trackDialog {
            matchTitleHasNoText()
            matchProgressBarIsNotDisplayed()
            matchDisableViewIsNotDisplayed()
        }*/

    }

}