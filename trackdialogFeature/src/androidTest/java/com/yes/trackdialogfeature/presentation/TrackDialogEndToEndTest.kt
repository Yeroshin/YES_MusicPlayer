package com.yes.trackdialogfeature.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yes.trackdialogfeature.TrackDialogTest
import com.yes.trackdialogfeature.di.components.DaggerTestTrackDialogComponent
import com.yes.trackdialogfeature.di.components.DaggerTrackDialogComponent
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
    class TestViewModel(
        getMenuUseCase: UseCase<GetMenuUseCase.Params, Menu>,
        saveTracksToPlaylistUseCase: SaveTracksToPlaylistUseCase,
        uiMapper: UiMapper,
        menuStack: ArrayDeque<MenuUi>,
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

        fun pushEvent(state: TrackDialogContract.State) =
            CoroutineScope(UnconfinedTestDispatcher()).launch {
                _uiState.emit(state)
            }

        override fun setEvent(event: TrackDialogContract.Event) {

        }
    }

    private val viewModel = TestViewModel(
        mockk< UseCase<GetMenuUseCase.Params, Menu>>(),
        mockk< SaveTracksToPlaylistUseCase>(),
        mockk< UiMapper>(),
        mockk< ArrayDeque<MenuUi>>()
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
    class MockViewModelFactory(private val viewModel: ViewModel) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return viewModel as T
        }
    }



    private val factory = DaggerTestTrackDialogComponent.builder()
        .testTrackDialogModule(TestTrackDialogModule(viewModel))
        .build()
        .getViewModelFactory()
    //private val factory = MockViewModelFactory(viewModel)
    private val dependency = TrackDialog.TrackDialogDependency(
        factory
    )
    private val trackDialogFactory = MockFragmentFactoryImpl(
        dependency
    )

    private lateinit var scenario: FragmentScenario<TrackDialog>

    @Before
    fun setUp() {
        //  MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        scenario = launchFragment(
            factory = trackDialogFactory
        )
    }
    @Test
    fun onInitShowsTrackDialogStateIdle() {
        scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)
        }
        trackDialog {
            matchTitleHasNoText()
            matchProgressBarIsNotDisplayed()
            matchDisableViewIsNotDisplayed()
        }
        viewModel.pushEvent(
            TrackDialogContract.State(
                TrackDialogContract.TrackDialogState.Loading
            )
        )

        trackDialog {
            matchProgressBarDisplayed()
        }
    }

}