package com.yes.trackdialogfeature.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.test.core.app.ApplicationProvider
import com.yes.trackdialogfeature.di.components.DaggerTestTrackDialogComponent
import com.yes.trackdialogfeature.di.module.TestAppModule
import com.yes.trackdialogfeature.di.module.TestTrackDialogModule
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import com.yes.trackdialogfeature.trackDialog
import kotlinx.coroutines.test.runTest
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
    @Test
    fun onInitShowsTrackDialogStateIdle() = runTest{
       /* trackDialog {
            matchTitleHasNoText()
            //  matchProgressBarIsNotDisplayed()
            // matchDisableViewIsNotDisplayed()
        }*/

        scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)
            trackDialog {
                matchTitleHasNoText()
                //  matchProgressBarIsNotDisplayed()
                // matchDisableViewIsNotDisplayed()
            }
        }

    }

}