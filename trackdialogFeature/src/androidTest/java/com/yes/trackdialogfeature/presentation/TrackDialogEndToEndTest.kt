package com.yes.trackdialogfeature.presentation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import com.yes.trackdialogfeature.TrackDialogTest
import com.yes.trackdialogfeature.data.repository.dataSource.PlayListDataBase
import com.yes.trackdialogfeature.di.components.DaggerTestTrackDialogComponent
import com.yes.trackdialogfeature.di.components.DaggerTrackDialogComponent
import com.yes.trackdialogfeature.di.module.TestAppModule
import com.yes.trackdialogfeature.di.module.TestTrackDialogModule
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase
import com.yes.trackdialogfeature.domain.usecase.UseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import com.yes.trackdialogfeature.presentation.ui.TrackDialogAdapter
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import com.yes.trackdialogfeature.trackDialog
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import java.util.ArrayDeque
import javax.inject.Inject

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
    class TestContainerActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
          //  setContentView(R.layout.activity_test_container)
        }
    }
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
        val scenario:FragmentScenario<TrackDialog> = launchFragment(
            factory = trackDialogFactory
        )
       /* scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)
        }*/
    }
    @Test
    fun onInitShowsTrackDialogStateIdle() {

        trackDialog {
            matchTitleHasNoText()
          //  matchProgressBarIsNotDisplayed()
           // matchDisableViewIsNotDisplayed()
        }
    }

}