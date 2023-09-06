package com.yes.trackdialogfeature.presentation

import android.Manifest
import android.content.Context
import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import com.example.shared_test.MediaFileFixtures
import com.example.shared_test.UiFixtures
import com.yes.core.util.EspressoIdlingResource
import com.yes.trackdialogfeature.di.components.DaggerTestTrackDialogComponent
import com.yes.trackdialogfeature.di.components.TestTrackDialogComponent
import com.yes.trackdialogfeature.di.module.TestAppModule
import com.yes.trackdialogfeature.di.module.TestTrackDialogModule
import com.yes.trackdialogfeature.domain.repository.IPlayListDao
import com.yes.trackdialogfeature.domain.repository.ISettingsRepository
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import com.yes.trackdialogfeature.trackDialog
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class TrackDialogEndToEndTest {

    @JvmField
    @Rule
    var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE)

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

    private lateinit var scenario: FragmentScenario<TrackDialog>
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val mediaFileFixtures = MediaFileFixtures(context)
    private lateinit var di: TestTrackDialogComponent
    private lateinit var settings: ISettingsRepository
    private lateinit var dataBase: IPlayListDao

    @Before
    fun setUp() {
        mediaFileFixtures.writeNonExistFiles()
//////////////////////////////
        //   testMediaFileCreator.createTestMediaFilesFromAssets()

        //  IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        /////////////////////////
        // val activity=MyActivity()
        di = DaggerTestTrackDialogComponent.builder()
            .testTrackDialogModule(TestTrackDialogModule())
            .testAppModule(TestAppModule(ApplicationProvider.getApplicationContext()))
            .build()
        val factory = di.getViewModelFactory()
        dataBase = di.getDataBase()
        settings=di.getSettings()
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
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    private val onClick: (TrackDialogContract.Event) -> Unit = {}

    @Test
    fun savesSelectedArtistTracksToPlaylist() {

        scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)

        }



        trackDialog {
            matchTitleText("categories")
            matchProgressBarIsNotDisplayed()
            clickItemInMediaList(0)

            matchTitleText("artists")
            matchProgressBarIsNotDisplayed()
            matchTrackDialogItemAtPosition(
                1,
                mediaFileFixtures.getArtists()[0]
            )
            clickItemInMediaList(
                1 + UiFixtures.getSelectedArtistIndex(),
            )
            matchTitleText(
                mediaFileFixtures.getArtists()[UiFixtures.getSelectedArtistIndex()].name
            )
            matchProgressBarIsNotDisplayed()
            matchTrackDialogItemAtPosition(
                UiFixtures.getSelectedArtistIndex(),
                mediaFileFixtures.getSelectedArtistTracks()[0]
            )
            clickItemCheckBoxInMediaList(
                1+UiFixtures.getSelectedArtistSelectedTrack()
            )
            clickOkButton()
            matchSelectedArtistTracksSavedToPlaylist(
                mediaFileFixtures.getSelectedArtistSelectedTrack(),
                settings,
                dataBase
            )

        }

    }


    fun withCheckBoxAtPositionIsChecked(
        position: Int,
    ): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("with checkbox at position: $position ")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)

                val checkbox =
                    viewHolder?.itemView?.findViewById<CheckBox>(com.yes.trackdialogfeature.R.id.checkBox)
                return checkbox != null && checkbox.isChecked

            }
        }
    }

    object MyViewAction {
        fun clickChildViewWithId(id: Int): ViewAction {
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
    }
}