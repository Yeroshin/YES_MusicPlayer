package com.yes.trackdialogfeature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState

import com.yes.trackdialogfeature.domain.common.Result
import com.yes.trackdialogfeature.domain.common.UseCaseException
import com.yes.trackdialogfeature.domain.usecase.GetRootMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.ShowChildMenuUseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.MenuMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TrackDialogViewModel(
    private val getRootMenuUseCase: GetRootMenuUseCase,
    private val showChildMenuUseCase: ShowChildMenuUseCase,
    private val menuMapper: MenuMapper
) : ViewModel() {

    val currentState: TrackDialogContract.State
        get() = uiState.value

   /* private val initialState : UiState by lazy { createInitialState() }
    fun createInitialState() : UiState{
        return TrackDialogContract.State(TrackDialogContract.TrackDialogState.Loading)
    }*/



    private val _uiState = MutableStateFlow<TrackDialogContract.State>(
        TrackDialogContract.State(TrackDialogContract.TrackDialogState.Loading)
    )
    val uiState: StateFlow<TrackDialogContract.State> = _uiState

    private val _event: MutableSharedFlow<UiEvent> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<TrackDialogContract.Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
        viewModelScope.launch(Dispatchers.Main) {
            val result = getRootMenuUseCase(GetRootMenuUseCase.Params(Unit))
            when (result) {
                is Result.Success -> {
                    val item = menuMapper.map(
                        result.data,
                        ::setEvent
                    )
                    setState {copy(trackDialogState =TrackDialogContract.TrackDialogState.Success(item))  }
                }
                is Result.Error -> {}
            }
        }
    }
    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    fun setEvent(event: UiEvent) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    protected fun setState(reduce: TrackDialogContract.State.() -> TrackDialogContract.State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }
    private fun setEffect(builder: () -> TrackDialogContract.Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }



    private fun handleEvent(event: UiEvent) {
        when (event) {
            is TrackDialogContract.Event.OnItemClicked -> {
                onGetChildMenu(event.title, event.name)
            }

        }
    }



    /*  fun onEvent(event: TrackDialogEvent) {
          when (event) {
              is TrackDialogEvent.GetChildMenu -> onGetChildMenu(event.title, event.name)
          }
      }*/


    private fun onGetChildMenu(title: String, name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = showChildMenuUseCase(
                ShowChildMenuUseCase.Params(
                    title,
                    name
                )
            )
            when (result) {
                is Result.Success -> {
                    val item = menuMapper.map(
                        result.data,
                        ::setEvent
                    )
                    _uiState.value = TrackDialogContract.State(TrackDialogContract.TrackDialogState.Success(item))
                }
                is Result.Error -> {
                    when (result.data) {
                        is UseCaseException.UnknownException -> setEffect {
                            TrackDialogContract.Effect.UnknownException
                        }
                        else -> {}
                    }

                }
                else -> {}
            }
        }

    }

    class Factory(
        private val getRootMenuUseCase: GetRootMenuUseCase,
        private val showChildMenuUseCase: ShowChildMenuUseCase,
        private val menuMapper: MenuMapper
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrackDialogViewModel(
                getRootMenuUseCase,
                showChildMenuUseCase,
                menuMapper
            ) as T
        }
    }
}





