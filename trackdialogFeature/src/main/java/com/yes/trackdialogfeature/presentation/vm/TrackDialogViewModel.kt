package com.yes.trackdialogfeature.presentation.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.presentation.BaseViewModel

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.MenuException
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCaseOLD
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.MenuUiDomainMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TrackDialogViewModel(
    private val getChildMenuUseCaseOLD: GetChildMenuUseCaseOLD,
    private val menuUiDomainMapper: MenuUiDomainMapper,
    private val menuStack: ArrayDeque<MenuUi>
) : BaseViewModel<TrackDialogContract.Event,
        TrackDialogContract.State,
        TrackDialogContract.Effect>() {

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
            is TrackDialogContract.Event.OnItemBackClicked -> {
                getParentMenu()
            }
            is TrackDialogContract.Event.OnItemOkClicked -> {

            }
        }
    }
    private fun saveItems(){

    }

    private fun getParentMenu() {
        viewModelScope.launch(Dispatchers.Main) {
            setState {
                copy(
                    trackDialogState = TrackDialogContract.TrackDialogState.Success(
                        menuStack.removeLast()
                    )
                )
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
            val result = getChildMenuUseCaseOLD(
                GetChildMenuUseCaseOLD.Params(
                    id,
                    name
                )
            )
            when (result) {
                is DomainResult.Success -> setState {
                    val menuUi = menuUiDomainMapper.map(
                        result.data,
                        ::setEvent
                    )
                    if (!menuStack.isEmpty()) {
                        menuUi.items
                            .toMutableList()
                            .add(
                                MenuUi.MediaItem(
                                    0,
                                    "..",
                                    0,
                                    TrackDialogContract.Event.OnItemBackClicked,
                                    ::setEvent
                                )
                            )
                    }
                    if (!menuStack.offer(menuUi)) {
                        setEffect {
                            TrackDialogContract.Effect.UnknownException
                        }
                        return@setState copy(
                            trackDialogState = TrackDialogContract.TrackDialogState.Idle
                        )
                    }

                    copy(
                        trackDialogState = TrackDialogContract.TrackDialogState.Success(menuUi)
                    )
                }
                is DomainResult.Error -> {
                    when (result.exception) {
                        is DomainResult.UnknownException -> {
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
        private val getChildMenuUseCaseOLD: GetChildMenuUseCaseOLD,
        private val menuUiDomainMapper: MenuUiDomainMapper,
        private val menuStack: ArrayDeque<MenuUi>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return TrackDialogViewModel(
                getChildMenuUseCaseOLD,
                menuUiDomainMapper,
                menuStack
            ) as T
        }
    }
}







