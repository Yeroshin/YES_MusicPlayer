package com.yes.trackdialogfeature


import androidx.fragment.app.testing.launchFragment
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import com.yes.trackdialogfeature.presentation.ui.TrackDialogAdapter
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import io.mockk.*
import org.junit.Before
import org.junit.Test


//@RunWith(AndroidJUnit4::class)
class TrackDialogTest {
    private var viewModel: TrackDialogViewModel = mockk()
   // private val viewModel = mockkClass(TrackDialogViewModel::class)
    private val viewModelFactory = MockTrackDialogViewModelFactory(viewModel)
    private val adapter: TrackDialogAdapter = TrackDialogAdapter()
    private val trackDialogFactory = MockFragmentFactoryImpl(
        TrackDialog.TrackDialogDependency(
            viewModelFactory,
            adapter
        )
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create DetailViewModel before every test
    }

    @Test
    fun showsRootCategoriesSuccess() {
        /*  val appContext = InstrumentationRegistry.getInstrumentation().targetContext
          val n=appContext.packageName
          Assert.assertEquals("com.yes.trackdialogfeature.test", appContext.packageName)*/

        val expected = TrackDialogContract.State(
            TrackDialogContract.TrackDialogState.Idle
        )
       // justRun { viewModel.setEvent(any()) }
        every {
            viewModel.test(1) } returns 10
        every {
            viewModel.setEvent(TrackDialogContract.Event.OnItemClicked(0, ""))
        } returns Unit

        val scenario = launchFragment<TrackDialog>(
            factory = trackDialogFactory
        )

        scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)
        }
        /* val scenario = launchFragment<TrackDialog>()
           scenario.moveToState(Lifecycle.State.STARTED)
           scenario.onFragment { fragment ->
               assert(fragment.requireDialog().isShowing)
           }*/
    }
}