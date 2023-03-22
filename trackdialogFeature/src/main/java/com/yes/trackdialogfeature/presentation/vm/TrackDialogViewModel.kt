package com.yes.trackdialogfeature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.presentation.BaseViewModel

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.MenuDomainUiMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrackDialogViewModel(
    private val getChildMenuUseCase: GetChildMenuUseCase,
    private val menuDomainUiMapper: MenuDomainUiMapper
) : BaseViewModel<TrackDialogContract.Event,TrackDialogContract.State,TrackDialogContract.Effect>() {
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
            is TrackDialogContract.Event.OnGetRootMenu -> {
                getRootMenu()
            }

        }
    }


    private fun getRootMenu() {
        viewModelScope.launch(Dispatchers.Main) {
            setState {
                copy(
                    trackDialogState = TrackDialogContract.TrackDialogState.Loading
                )
            }
            val result = getChildMenuUseCase(GetChildMenuUseCase.Params(0,""))
            when (result) {
                is DomainResult.Success -> {
                    val item = menuDomainUiMapper.map(
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
                                menuDomainUiMapper.map(
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
        private val menuDomainUiMapper: MenuDomainUiMapper
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TrackDialogViewModel(
                getChildMenuUseCase,
                menuDomainUiMapper
            ) as T
        }
    }
}





