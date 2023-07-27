package com.yes.trackdialogfeature.presentation.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.presentation.BaseViewModel

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.MenuException
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase
import com.yes.trackdialogfeature.domain.usecase.UseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract.State
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract.Effect
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.model.MenuUi.ItemUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TrackDialogViewModel(
    private val getMenuUseCase: UseCase<GetMenuUseCase.Params, Menu>,
    private val saveTracksToPlaylistUseCase: SaveTracksToPlaylistUseCase,
    private val uiMapper: UiMapper,
    private val menuStack: ArrayDeque<MenuUi>,
) : BaseViewModel<TrackDialogContract.Event,
        State,
        Effect>() {

    override fun createInitialState(): State {
        return State(
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

            is TrackDialogContract.Event.OnButtonOkClicked -> {
                saveItems(event.items)
            }

            is TrackDialogContract.Event.OnButtonCancelClicked -> {
                dismiss()
            }
        }
    }

    private fun dismiss() {
        setState {
            copy(
                trackDialogState = TrackDialogContract.TrackDialogState.Dismiss
            )
        }
    }

    private fun saveItems(items:List<ItemUi>) {
        viewModelScope.launch(Dispatchers.Main) {
            saveTracksToPlaylistUseCase(
                SaveTracksToPlaylistUseCase.Params(
                    //to do - filter back item
                    items.map {
                        uiMapper.map(it)
                    }
                )
            )
            dismiss()
        }

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

    private fun getChildMenu(id: Int?, name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            setState {
                copy(
                    trackDialogState = TrackDialogContract.TrackDialogState.Loading
                )
            }
            val params=id?.let {
                GetMenuUseCase.Params(
                    it,
                    name
                )
            }

            val result = getMenuUseCase(
                params
            )
            when (result) {
                is DomainResult.Success -> setState {
                    val menuUi = uiMapper.map(
                        result.data,
                        ::setEvent
                    )
                    if (!menuStack.isEmpty()) {
                        menuUi.items
                            .toMutableList()
                            .add(0,
                                ItemUi(
                                    -1,
                                    "..",
                                    0,
                                    null,
                                    TrackDialogContract.Event.OnItemBackClicked,
                                    ::setEvent
                                )
                            )
                    }
                    if (!menuStack.offer(menuUi)) {
                        setEffect {
                            Effect.UnknownException
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
                                Effect.UnknownException
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
        private val getMenuUseCase: GetMenuUseCase,
        private val saveTracksToPlaylistUseCase: SaveTracksToPlaylistUseCase,
        private val uiMapper: UiMapper,
        private val menuStack: ArrayDeque<MenuUi>,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return TrackDialogViewModel(
                getMenuUseCase,
                saveTracksToPlaylistUseCase,
                uiMapper,
                menuStack,
            ) as T
        }
    }
}







