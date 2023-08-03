package com.yes.trackdialogfeature.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yes.trackdialogfeature.TrackDialogTest
import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.data.repository.MenuRepositoryImpl
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase
import com.yes.trackdialogfeature.domain.usecase.UseCase
import com.yes.trackdialogfeature.presentation.TrackDialogEndToEndTest
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import java.util.ArrayDeque

@Module
class TestTrackDialogModule(private val viewModel: TrackDialogEndToEndTest.TestViewModel) {
    class MockViewModelFactory(private val viewModel: TrackDialogEndToEndTest.TestViewModel) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return viewModel as T
        }
    }
  /*  class TestViewModel(
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
    }*/


  /*  @Provides
    fun providesTestViewModel():TestViewModel{
        return TestViewModel(
            mockk< UseCase<GetMenuUseCase.Params, Menu>>(),
            mockk< SaveTracksToPlaylistUseCase>(),
            mockk< UiMapper>(),
            mockk< ArrayDeque<MenuUi>>()
        )
    }*/
    @Provides
    fun providesTrackDialogViewModelFactory(

    ): ViewModelProvider.Factory {
        return MockViewModelFactory(viewModel)
    }

}