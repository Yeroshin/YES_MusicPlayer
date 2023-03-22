package com.yes.trackdialogfeature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.MenuMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TrackDialogViewModel(
    private val getChildMenuUseCase: GetChildMenuUseCase,
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

    private val _event: MutableSharedFlow<TrackDialogContract.Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<TrackDialogContract.Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()

    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    fun setEvent(event: TrackDialogContract.Event) {
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


    private fun handleEvent(event: TrackDialogContract.Event) {
        when (event) {
            is TrackDialogContract.Event.OnItemClicked -> {
                getChildMenu(event.id, event.name)
            }
            is TrackDialogContract.Event.OnGetRootMenu -> {
                getRootMenu()
            }

        }
    }


    /*  fun onEvent(event: TrackDialogEvent) {
          when (event) {
              is TrackDialogEvent.GetChildMenu -> onGetChildMenu(event.title, event.name)
          }
      }*/


    private fun getRootMenu() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = getChildMenuUseCase(GetChildMenuUseCase.Params(0,""))
            when (result) {
                is DomainResult.Success -> {
                    val item = menuMapper.map(
                        result.data,
                        ::setEvent
                    )
                    setState {
                        copy(
                            trackDialogState = TrackDialogContract.TrackDialogState.Success(
                                item
                            )
                        )
                    }
                }
                is DomainResult.Error -> {}
            }
        }
    }

    private fun getChildMenu(id:Int, name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = getChildMenuUseCase(
                GetChildMenuUseCase.Params(
                    id,
                    name
                )
            )
            when (result) {
                is DomainResult.Success -> setState {
                        TrackDialogContract.State(
                            TrackDialogContract.TrackDialogState.Success(
                                menuMapper.map(
                                    result.data,
                                    ::setEvent
                                )
                            )
                        )
                    }

                is DomainResult.Error -> {
                    when (result.exception) {
                        is Exception-> setEffect {
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
        private val getChildMenuUseCase: GetChildMenuUseCase,
        private val menuMapper: MenuMapper
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrackDialogViewModel(
                getChildMenuUseCase,
                menuMapper
            ) as T
        }
    }
}





