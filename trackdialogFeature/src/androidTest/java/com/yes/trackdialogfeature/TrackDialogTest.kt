package com.yes.trackdialogfeature


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.util.copy
import androidx.test.espresso.IdlingRegistry
import com.example.shared_test.UiFixturesGenerator
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase
import com.yes.trackdialogfeature.domain.usecase.UseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import com.yes.trackdialogfeature.util.EspressoIdlingResource
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.ArrayDeque


class TrackDialogTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher: TestDispatcher = StandardTestDispatcher()

    class TestViewModel(
        getMenuUseCase: UseCase<GetMenuUseCase.Params, Menu>,
        saveTracksToPlaylistUseCase: SaveTracksToPlaylistUseCase,
        uiMapper: UiMapper,
        menuStack: ArrayDeque<MenuUi>,
        testDispatcher: TestDispatcher,
    ) : TrackDialogViewModel(
        getMenuUseCase,
        saveTracksToPlaylistUseCase,
        uiMapper,
        menuStack,
    ) {

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

        @OptIn(ExperimentalCoroutinesApi::class)
        private val scope = TestScope(testDispatcher)

        /* fun pushEvent(state: TrackDialogContract.State) =
             scope.launch {
                 _uiState.emit(state)
             }*/
        /* fun pushEvent(reduce: TrackDialogContract.State.() -> TrackDialogContract.State) {
            EspressoIdlingResource.increment()
            val newState = currentState.reduce()
            _uiState.value = newState
            EspressoIdlingResource.decrement()
        }*/
        @OptIn(ExperimentalCoroutinesApi::class)
        fun pushEvent(reduce: TrackDialogContract.State.() -> TrackDialogContract.State) {
            EspressoIdlingResource.increment()
            val newState = currentState.reduce()
            //  launch {
            _uiState.value = newState
            //  }
            EspressoIdlingResource.decrement()

        }

      /* fun pushEvent(newState: TrackDialogContract.State) {

            //_uiState.emit(newState)
            _uiState.value = newState


        }*/

        override fun setEvent(event: TrackDialogContract.Event) {

        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private val viewModel = TestViewModel(
        mockk(),
        mockk(),
        mockk(),
        mockk(),
        testDispatcher
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
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        scenario = launchFragment(
            factory = trackDialogFactory,
            //  initialState = Lifecycle.State.STARTED
        )

    }

    @After
    fun tearDown() {
      //  Dispatchers.resetMain()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        scenario.close()
    }

    @Test
    fun onInitShowsTrackDialogStateIdle() = runBlocking{
         scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)
          //  IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
           /*  viewModel.pushEvent(
                 TrackDialogContract.State(
                     TrackDialogContract.TrackDialogState.Loading
                 )
             )*/
        }
     //   scenario.moveToState(Lifecycle.State.STARTED)
        trackDialog {
            matchTitleHasNoText()
            matchProgressBarIsNotDisplayed()
            matchDisableViewIsNotDisplayed()
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

  /*  @Test
    fun loading() {

        viewModel.pushEvent {
            copy(
                TrackDialogContract.TrackDialogState.Loading
            )
        }

        trackDialog {
            matchTitleHasNoText()
            matchProgressBarDisplayed()
            matchDisableViewDisplayed()
        }
    }

    @Test
    fun dataSuccess() {
        val number = 200
        val item = UiFixturesGenerator.generateArtistsMenuUi(number)
        viewModel.pushEvent {
            copy(
                TrackDialogContract.TrackDialogState.Success(
                    item
                )
            )
        }
        trackDialog {
            matchTitleText(item.title)
            matchProgressBarIsNotDisplayed()
            matchDisableViewIsNotDisplayed()
            matchTrackDialogItemAtPosition(
                number - 1,
                item.items[number - 1]
            )
        }
    }*/
}

//////////////////////worked
/*    onView(withId(com.yes.coreui.R.id.recyclerView)).perform(
        scrollToPosition<TrackDialogAdapter.TrackHolder>(
            0
        )
    )

    onView(withText("Artist")).check(matches(isDisplayed()))
    //onView(withText("Description")).check(matches(isDisplayed()))
    //////////////////////////////

    onView(withId(com.yes.coreui.R.id.recyclerView)) // Replace 'recycler_view' with the ID of your RecyclerView
        .check(matches(atPosition(0, R.id.item_title, "Album")))
*/
//////////////////////////////

/* private fun atPosition(position: Int, targetViewId: Int, expectedText: String): Matcher<View> {
     return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
         override fun describeTo(description: Description) {
             //description.appendText("has item at position $position: ")

         }

         override fun matchesSafely(recyclerView: RecyclerView): Boolean {
             val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
             val targetView = viewHolder?.itemView?.findViewById<TextView>(targetViewId)

             return targetView?.text == expectedText
         }
     }
 }*/
/*
private fun atPosition(position: Int, itemMatcher: Matcher<View>, expectedText: String?): Matcher<View> {
    checkNotNull(itemMatcher)
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
            if (viewHolder == null) {
                // has no item on such position
                return false
            }
            val itemView = viewHolder.itemView
            var matchResult = itemMatcher.matches(itemView)

            if (expectedText != null) {
                val textView = itemView.findViewById<TextView>(R.id.item_title)
                val actualText = textView.text.toString()
                matchResult = matchResult && actualText == expectedText
            }

            return matchResult
        }
    }
}
*/


