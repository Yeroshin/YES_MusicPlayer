package com.yes.trackdialogfeature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.presentation.BaseViewModel

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.MenuException
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.MenuUiDomainMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrackDialogViewModel(
    private val getChildMenuUseCase: GetChildMenuUseCase,
    private val menuUiDomainMapper: MenuUiDomainMapper
) : BaseViewModel<TrackDialogContract.Event, TrackDialogContract.State, TrackDialogContract.Effect>() {
    override fun createInitialState(): TrackDialogContract.State {
        return TrackDialogContract.State(
            TrackDialogContract.TrackDialogState.Idle
        )
    }

    override fun handleEvent(event: TrackDialogContract.Event) {
        when (event) {
            is TrackDialogContract.Event.OnItemClicked -> {
                getChildMenu(event.id, event.name)
            }

        }
    }

    private fun getChildMenu(id: Int, name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            setState {
                copy(
                    trackDialogState = TrackDialogContract.TrackDialogState.Loading
                )
            }
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
                            menuUiDomainMapper.map(
                                result.data,
                                ::setEvent
                            )
                        )
                    )
                }
                is DomainResult.Error -> {
                    when (result.exception) {
                        is MenuException.UnknownException -> {
                            setState {
                                copy(
                                    trackDialogState = TrackDialogContract.TrackDialogState.Idle
                                )
                            }
                            setEffect {
                                TrackDialogContract.Effect.UnknownException
                            }
                        }
                        is MenuException.Empty -> {
                            setState {
                                copy(
                                    trackDialogState = TrackDialogContract.TrackDialogState.Idle
                                )
                            }
                        }
                    }
                }
            }
        }

    }

    class Factory(
        private val getChildMenuUseCase: GetChildMenuUseCase,
        private val menuUiDomainMapper: MenuUiDomainMapper
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrackDialogViewModel(
                getChildMenuUseCase,
                menuUiDomainMapper
            ) as T
        }
    }
}





