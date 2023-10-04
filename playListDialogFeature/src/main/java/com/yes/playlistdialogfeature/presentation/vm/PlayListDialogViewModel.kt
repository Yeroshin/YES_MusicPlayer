package com.yes.playlistdialogfeature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.presentation.BaseViewModel
import com.yes.core.util.EspressoIdlingResource
import com.yes.playlistdialogfeature.presentation.contract.PlayListDialogContract
import com.yes.playlistdialogfeature.presentation.contract.PlayListDialogContract.Event
import com.yes.playlistdialogfeature.presentation.contract.PlayListDialogContract.State
import com.yes.playlistdialogfeature.presentation.contract.PlayListDialogContract.Effect
import com.yes.playlistdialogfeature.presentation.mapper.UiMapper
import com.yes.playlistdialogfeature.presentation.model.ItemUi
import kotlinx.coroutines.launch

class PlayListDialogViewModel(
    private val subscribePlayLists: SubscribePlayListsUseCase,
    private val addPlayListUseCase: AddPlayListUseCase,
    private val setPlaylistUseCase: SetPlaylistUseCase,
    private val uiMapper: UiMapper,
    private val espressoIdlingResource: EspressoIdlingResource?
) :
    BaseViewModel<Event, State, Effect>() {
    override fun createInitialState(): State {
        return State(
            PlayListDialogContract.PlayListDialogState.Idle
        )
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnButtonAddClicked -> {
                addPlayList()
            }

            is Event.OnButtonCancelClicked -> {
                dismiss()
            }

            is Event.OnButtonOkClicked -> {
                setPlaylist(event.items)
            }
        }
    }

    private fun subscribePlaylists() {
        viewModelScope.launch {
            val result = subscribePlaylists()
        }
    }

    private fun setPlaylist(items: List<ItemUi>) {
        viewModelScope.launch {
            setState {
                copy(
                    playListDialogState = PlayListDialogContract.PlayListDialogState.Loading
                )
            }
            val result = subscribePlaylists()
        }
    }

    private fun addPlayList() {

    }

    private fun dismiss() {
        setState {
            copy(
                playListDialogState = PlayListDialogContract.PlayListDialogState.Dismiss
            )
        }
    }

    class Factory(
        private val subscribePlayLists: SubscribePlayListsUseCase,
        private val addPlayListUseCase: AddPlayListUseCase,
        private val setPlaylistUseCase: SetPlaylistUseCase,
        private val uiMapper: UiMapper,
        private val espressoIdlingResource: EspressoIdlingResource?
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PlayListDialogViewModel(
                subscribePlayLists,
                addPlayListUseCase,
                setPlaylistUseCase,
                uiMapper,
                espressoIdlingResource,
            ) as T
        }
    }
}