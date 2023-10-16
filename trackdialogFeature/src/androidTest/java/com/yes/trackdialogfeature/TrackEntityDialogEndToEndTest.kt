package com.yes.trackdialogfeature

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import com.example.shared_test.MediaFileFixtures
import com.example.shared_test.UiFixtures
import com.yes.core.util.EspressoIdlingResource
import com.yes.trackdialogfeature.di.components.DaggerTestTrackDialogComponent
import com.yes.trackdialogfeature.di.components.TestTrackDialogComponent
import com.yes.trackdialogfeature.di.module.TestAppModule
import com.yes.trackdialogfeature.di.module.TestTrackDialogModule
import com.yes.core.domain.repository.IPlayListDao
import com.yes.trackdialogfeature.domain.repository.SettingsRepository
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import org.junit.After
import org.junit.Before
import org.junit.Test


class TrackEntityDialogEndToEndTest {

    /*  @JvmField
      @Rule
      var permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE)
  */
    class MockFragmentFactoryImpl(
        private val dep: TrackDialog.Dependency
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
    private lateinit var settings: SettingsRepository
    private lateinit var dataBase: IPlayListDao

    @Before
    fun setUp() {
        mediaFileFixtures.writeNonExistFiles()

        di = DaggerTestTrackDialogComponent.builder()
            .testTrackDialogModule(TestTrackDialogModule())
            .testAppModule(TestAppModule(ApplicationProvider.getApplicationContext()))
            .build()
        val factory = di.getViewModelFactory()
        dataBase = di.getDataBase()
        settings = di.getSettings()
        val dependency = TrackDialog.Dependency(
            factory
        )
        val trackDialogFactory = MockFragmentFactoryImpl(
            dependency
        )
        scenario = launchFragment(
            factory = trackDialogFactory
        )

    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun testNavigationOnMenu() {
        trackDialog {

            //going three categories forward and then back to first
            matchTitleText("categories")
            matchProgressBarIsNotDisplayed()
            notMatchTrackDialogItemAtPosition(
                0,
                UiFixtures.getBackItem()
            )
//////////////////////////////////////////
            clickItemInMediaList(0)
            matchTitleText("artists")
            matchProgressBarIsNotDisplayed()
            matchTrackDialogItemAtPosition(
                0,
                UiFixtures.getBackItem()
            )
            matchTrackDialogItemAtPosition(
                1,
                mediaFileFixtures.getArtists()[0]
            )
///////////////////////////////////////
            clickItemInMediaList(
                1 + UiFixtures.getSelectedArtistIndex(),
            )
            matchTitleText(
                mediaFileFixtures.getArtists()[UiFixtures.getSelectedArtistIndex()].name
            )
            matchProgressBarIsNotDisplayed()
            matchTrackDialogItemAtPosition(
                0,
                UiFixtures.getBackItem()
            )
            matchTrackDialogItemAtPosition(
                UiFixtures.getSelectedArtistIndex(),
                mediaFileFixtures.getSelectedArtistTracks()[0]
            )
//clicking last category item second time should nothing happened
            clickItemInMediaList(
                1,
            )
            matchTitleText(
                mediaFileFixtures.getArtists()[UiFixtures.getSelectedArtistIndex()].name
            )
            matchProgressBarIsNotDisplayed()
            matchTrackDialogItemAtPosition(
                0,
                UiFixtures.getBackItem()
            )
            matchTrackDialogItemAtPosition(
                UiFixtures.getSelectedArtistIndex(),
                mediaFileFixtures.getSelectedArtistTracks()[0]
            )

            //going back
            clickItemInMediaList(0)
            matchTitleText("artists")
            matchProgressBarIsNotDisplayed()
            matchTrackDialogItemAtPosition(
                0,
                UiFixtures.getBackItem()
            )
            matchTrackDialogItemAtPosition(
                1,
                mediaFileFixtures.getArtists()[0]
            )

            clickItemInMediaList(0)
            matchTitleText("categories")
            matchProgressBarIsNotDisplayed()
            notMatchTrackDialogItemAtPosition(
                0,
                UiFixtures.getBackItem()
            )
            //going one category forward and then back
            clickItemInMediaList(0)
            matchTitleText("artists")
            matchProgressBarIsNotDisplayed()
            matchTrackDialogItemAtPosition(
                0,
                UiFixtures.getBackItem()
            )
            matchTrackDialogItemAtPosition(
                1,
                mediaFileFixtures.getArtists()[0]
            )

            clickItemInMediaList(0)
            matchTitleText("categories")
            matchProgressBarIsNotDisplayed()
            notMatchTrackDialogItemAtPosition(
                0,
                UiFixtures.getBackItem()
            )
        }
    }

    @Test
    fun savesSelectedArtistSelectedTracksToPlaylist() {

        scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)

        }

        trackDialog {

            clickItemInMediaList(0)
            clickItemInMediaList(
                1 + UiFixtures.getSelectedArtistIndex(),
            )
            clickItemCheckBoxInMediaList(
                1 + UiFixtures.getSelectedArtistSelectedTrack()
            )
            clickOkButton()
            matchSelectedTracksSavedToPlaylist(
                mediaFileFixtures.getSelectedArtistSelectedTrack(),
                settings,
                dataBase
            )

        }

    }

    @Test
    fun savesNetworkSourceToPlaylist(){
        trackDialog {
            clickNetworkSourceButton()
            enterAddressNetworkSource(
                UiFixtures.getNetworkPath()
            )
            clickOkButton()
            matchNetworkSourceSavedToPlaylist(
                UiFixtures.getNetworkTrack(),
                settings,
                dataBase

            )
        }
    }

}