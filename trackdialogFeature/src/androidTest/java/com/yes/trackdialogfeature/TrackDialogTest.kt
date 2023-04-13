package com.yes.trackdialogfeature


import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.yes.core.presentation.IBaseViewModel
import com.yes.trackdialogfeature.databinding.TrackDialogBinding
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import com.yes.trackdialogfeature.presentation.ui.TrackDialogAdapter
import io.mockk.*
import kotlinx.coroutines.flow.*
import org.junit.Before
import org.junit.Test


class TrackDialogTest {
    /*  private val getChildMenuUseCase: GetChildMenuUseCase=mockk()

      private val viewModel = spyk(
          TrackDialogViewModel(getChildMenuUseCase, menuUiDomainMapper),
          recordPrivateCalls = true
      ){

      }
     // private val viewModel = spyk()

     // private val menuUiDomainMapper: MenuUiDomainMapper = mockk()
      /*   val viewModel = spyk(TrackDialogViewModel(any)) {
             every {
                 test()
                /* setEvent(
                     any()
                    // TrackDialogContract.Event.OnItemClicked(0, "")
                 )*/
             } returns 10
         }*/

     */
    val vm=object: IBaseViewModel<TrackDialogContract.Event,
            TrackDialogContract.State,
            TrackDialogContract.Effect>  {
        override val uiState: StateFlow<TrackDialogContract.State>
            get() = TODO("Not yet implemented")
        override val effect: Flow<TrackDialogContract.Effect>
            get() = TODO("Not yet implemented")

        override fun setEvent(event: TrackDialogContract.Event) {
            TODO("Not yet implemented")
        }

    }

    private val viewModel: IBaseViewModel<TrackDialogContract.Event,
            TrackDialogContract.State,
            TrackDialogContract.Effect> = mockk(relaxed = true)
   private val adapter: TrackDialogAdapter = mockk(relaxed = true)
    private val dependency = TrackDialog.TrackDialogDependency(
        viewModel,
        adapter
    )
    private val trackDialogFactory = MockFragmentFactoryImpl(
        dependency
    )
    private lateinit var scenario: FragmentScenario<TrackDialog>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create DetailViewModel before every test
        every {
            viewModel.uiState
        } answers {
            MutableStateFlow(
                TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            ).asStateFlow()
        }

        scenario = launchFragment(
            factory = trackDialogFactory
        )
    }

    @Test
    fun onInitShowsTrackDialogStateIdle() {

        val expected = TrackDialogContract.State(
            TrackDialogContract.TrackDialogState.Idle
        )
        /* every {
             viewModel.setEvent(any())
         } returns Unit*/
        /*  every {
              viewModel.uiState
          } answers { MutableStateFlow(TrackDialogContract.State(TrackDialogContract.TrackDialogState.Idle)).asStateFlow() }
         */
        scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)
        }
      //  onView(withId(R.id.recyclerViewContainer.))
        // justRun { viewModel.setEvent(any()) }
        /* every {
             viewModel.test(1) } returns 10*/
        /*  val v=viewModel.test(1)
          assert(viewModel.test(1) == 10)*/
        /* every {
             viewModel.setEvent(TrackDialogContract.Event.OnItemClicked(0, ""))
         } returns Unit*/


        /* val scenario = launchFragment<TrackDialog>()
           scenario.moveToState(Lifecycle.State.STARTED)
           scenario.onFragment { fragment ->
               assert(fragment.requireDialog().isShowing)
           }*/
    }
}