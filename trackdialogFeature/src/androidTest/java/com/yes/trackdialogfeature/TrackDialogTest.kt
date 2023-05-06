package com.yes.trackdialogfeature


import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.FragmentScenario.Companion.launch
import androidx.fragment.app.testing.launchFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.yes.core.presentation.IBaseViewModel
import com.yes.core.presentation.UiEffect
import com.yes.trackdialogfeature.databinding.TrackDialogBinding
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import com.yes.trackdialogfeature.presentation.ui.TrackDialogAdapter
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import io.mockk.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test

@OptIn(
    FlowPreview::class,
    ExperimentalCoroutinesApi::class
)
class TrackDialogTest {


    //   private val viewModel:TrackDialogViewModel = mockk(relaxed = true)
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


    /*  val vm = object : IBaseViewModel<TrackDialogContract.Event,
              TrackDialogContract.State,
              TrackDialogContract.Effect> {
          override val uiState: StateFlow<TrackDialogContract.State>
              get() = TODO("Not yet implemented")
          override val effect: Flow<TrackDialogContract.Effect>
              get() = TODO("Not yet implemented")

          override fun setEvent(event: TrackDialogContract.Event) {
              TODO("Not yet implemented")
          }


      }*/

    /* private val viewModel: IBaseViewModel<TrackDialogContract.Event,
             TrackDialogContract.State,
             TrackDialogContract.Effect> = mockk(relaxed = true)*/
    class TestViewModel(

    ) : IBaseViewModel<TrackDialogContract.Event, TrackDialogContract.State, TrackDialogContract.Effect> {

        override val effect: Flow<TrackDialogContract.Effect> = flow {}
        private val events = MutableSharedFlow<Int>(0)

        private val _uiState: MutableStateFlow<TrackDialogContract.State> = MutableStateFlow(
            TrackDialogContract.State(
                TrackDialogContract.TrackDialogState.Idle
            )
        )

        //  override val uiState: StateFlow<TrackDialogContract.State>
        override val uiState: StateFlow<TrackDialogContract.State> =
            _uiState.flatMapConcat { state ->
                flow { emit(state) }
            }.stateIn(
                CoroutineScope(UnconfinedTestDispatcher()),
                SharingStarted.Lazily,
                TrackDialogContract.State(
                    TrackDialogContract.TrackDialogState.Idle
                )
            )

        /*  override val uiState: StateFlow<TrackDialogContract.State> by lazy {
              events.flatMapConcat { state ->
                  flow { emit(state) }
                      .onStart { emit(state ) }
                      .onCompletion { emit(state ) }
              }.stateIn(CoroutineScope(UnconfinedTestDispatcher()), SharingStarted.Lazily, 0)
          }*/
        fun pushEvent(state: TrackDialogContract.State) =
            CoroutineScope(UnconfinedTestDispatcher()).launch {
                _uiState.emit(state)
            }

        override fun setEvent(event: TrackDialogContract.Event) {

        }
    }

    val viewMOdel = TestViewModel()
    private val adapter: TrackDialogAdapter = mockk(relaxed = true)
    private val dependency = TrackDialog.TrackDialogDependency(
        viewMOdel,
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
        /*  every {
              viewModel.uiState
          } answers {
              MutableStateFlow(
                  TrackDialogContract.State(
                      TrackDialogContract.TrackDialogState.Idle
                  )
              ).asStateFlow()
          }*/
        scenario = launchFragment(
            factory = trackDialogFactory
        )
    }

    @Test
    fun onInitShowsTrackDialogStateIdle() = runTest {

        /* every {
             viewModel.setEvent(any())
         } returns Unit*/
        /*  every {
              viewModel.uiState
          } answers { MutableStateFlow(TrackDialogContract.State(TrackDialogContract.TrackDialogState.Idle)).asStateFlow() }
         */
        /*  coEvery {
              viewModel.uiState
          } returns TrackDialogContract.State(
              TrackDialogContract.TrackDialogState.Idle
          )*/

        scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)
        }
        /*  onView(withId(com.yes.coreui.R.id.dialogTitle))
              .check(matches(withText("")))
          onView(withId(com.yes.coreui.R.id.progressBar))
              .check(matches(not(isDisplayed())))
          onView(withId(com.yes.coreui.R.id.disableView))
              .check(matches(not(isDisplayed())))*/
        //////////////////////////////
        trackDialog {
            matchTitleHasNoText()
            matchProgressBarIsNotDisplayed()
            matchDisableViewIsNotDisplayed()
        }
        viewMOdel.pushEvent(
            TrackDialogContract.State(
                TrackDialogContract.TrackDialogState.Loading
            )
        )
        trackDialog {
            matchProgressBarDisplayed()
        }
        /*  vm.uiState.value =TrackDialogContract.State(
              TrackDialogContract.TrackDialogState.Idle
          )*/
        //////////////////////////////
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

    @Test
    fun loadDataSuccess() {

    }
}