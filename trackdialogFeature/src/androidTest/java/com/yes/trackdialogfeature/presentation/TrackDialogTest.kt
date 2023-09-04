package com.yes.trackdialogfeature.presentation


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shared_test.UiFixturesGenerator
import com.yes.core.presentation.BaseViewModel
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import com.yes.trackdialogfeature.trackDialog
import kotlinx.coroutines.flow.*
import org.junit.After
import org.junit.Before
import org.junit.Test


class TrackDialogTest {

    class TestViewModel : BaseViewModel<TrackDialogContract.Event,
            TrackDialogContract.State,
            TrackDialogContract.Effect>(null) {

        override val effect: Flow<TrackDialogContract.Effect> = flow {}
        override fun handleEvent(event: TrackDialogContract.Event) {
            TODO("Not yet implemented")
        }

        override fun createInitialState(): TrackDialogContract.State {
            return TrackDialogContract.State(
                TrackDialogContract.TrackDialogState.Idle
            )
        }

        private val _uiState: MutableStateFlow<TrackDialogContract.State> = MutableStateFlow(
            TrackDialogContract.State(
                TrackDialogContract.TrackDialogState.Idle
            )
        )
        override val uiState = _uiState.asStateFlow()


        fun pushEvent(reduce: TrackDialogContract.State.() -> TrackDialogContract.State) {
            //  EspressoIdlingResource.increment()
            val newState = currentState.reduce()
            //  val job=launch {
            _uiState.value = newState
            //  }
            //   job.cancel() // Отменить выполнение корутины
            //    job.join()
            // advanceUntilIdle()
            //  EspressoIdlingResource.decrement()

        }

        fun pushEvent(state: TrackDialogContract.TrackDialogState) {
            //  EspressoIdlingResource.increment()
            val newState = currentState.copy(
                trackDialogState = state
            )
            _uiState.value = newState
           /* viewModelScope.launch(Dispatchers.Main){
                _uiState.value = newState
            }*/

            //  }
            //   job.cancel() // Отменить выполнение корутины
            //    job.join()
            // advanceUntilIdle()
            //  EspressoIdlingResource.decrement()

        }

        /* fun pushEvent(newState: TrackDialogContract.State) {

              //_uiState.emit(newState)
              _uiState.value = newState


          }*/

        override fun setEvent(event: TrackDialogContract.Event) {

        }
    }



    private val viewModel = TestViewModel(

    )

    class MockViewModelFactory(private val viewModel: TestViewModel) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return viewModel as T
            /* if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
                 return viewModel  as T
             }else{
                 throw IllegalArgumentException(" lol Unknown ViewModel class")
             }*/

        }
    }


    private val factory = MockViewModelFactory(viewModel)
    private val dependency = TrackDialog.TrackDialogDependency(
        factory
    )

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

    private val trackDialogFactory = MockFragmentFactoryImpl(
        dependency
    )
    private lateinit var scenario: FragmentScenario<TrackDialog>


    @Before
    fun setUp() {
        // Dispatchers.setMain(testDispatcher)
        //  IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        //  MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        scenario = launchFragment(
            factory = trackDialogFactory,
            //  initialState = Lifecycle.State.STARTED
        )

    }

    @After
    fun tearDown() {
        //  Dispatchers.resetMain()
        // IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        scenario.close()
    }

    @Test
    fun onInitShowsTrackDialogStateIdle() {
        scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)
            // fragment.showLoading()
        }
        trackDialog {
            matchTitleHasNoText()
            matchProgressBarIsNotDisplayed()
           // matchDisableViewIsNotDisplayed()
        }
        viewModel.pushEvent {
            copy(
                trackDialogState = TrackDialogContract.TrackDialogState.Loading
            )
        }

        trackDialog {
            matchProgressBarDisplayed()
        }

    }

    @Test
    fun loading() {
        trackDialog {
            matchTitleHasNoText()
            matchProgressBarIsNotDisplayed()
        }
        viewModel.pushEvent {
            copy(
                TrackDialogContract.TrackDialogState.Loading
            )
        }

        trackDialog {
            matchTitleHasNoText()
            matchProgressBarDisplayed()
        }
    }

    @Test
    fun dataSuccess() {
        //////////////////////////////////////
       /* viewModel.pushEvent {
            copy(
                TrackDialogContract.TrackDialogState.Loading
            )
        }*/
        viewModel.pushEvent (

                TrackDialogContract.TrackDialogState.Loading

            )

        trackDialog {
            matchTitleHasNoText()
            matchProgressBarDisplayed()
        }
        //////////////////////////////////////
        val number = 200
        val item = UiFixturesGenerator.generateArtistsMenuUi(number)
       /* viewModel.pushEvent {
            copy(
                TrackDialogContract.TrackDialogState.Success(
                    item
                )
            )
        }*/
        viewModel.pushEvent (

            TrackDialogContract.TrackDialogState.Success(
                item
            )

        )
        trackDialog {
            matchTitleText(item.title)
            matchProgressBarIsNotDisplayed()
            matchTrackDialogItemAtPosition(
                number - 1,
                item.items[number - 1]
            )
        }
    }
}




