package com.yes.playlistdialogfeature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import com.yes.core.util.EspressoIdlingResource
import com.yes.playlistdialogfeature.domain.entity.PlaylistException
import com.yes.playlistdialogfeature.domain.usecase.AddPlayListUseCase
import com.yes.playlistdialogfeature.domain.usecase.DeletePlayListUseCase
import com.yes.playlistdialogfeature.domain.usecase.SetPlaylistUseCase
import com.yes.playlistdialogfeature.domain.usecase.SubscribePlayListsUseCase
import com.yes.playlistdialogfeature.presentation.contract.PlayListDialogContract
import com.yes.playlistdialogfeature.presentation.contract.PlayListDialogContract.Event
import com.yes.playlistdialogfeature.presentation.contract.PlayListDialogContract.State
import com.yes.playlistdialogfeature.presentation.contract.PlayListDialogContract.Effect
import com.yes.playlistdialogfeature.presentation.mapper.UiMapper
import com.yes.playlistdialogfeature.presentation.model.ItemUi

import kotlinx.coroutines.launch

class PlayListDialogViewModel(
    private val subscribePlayListsUseCase: SubscribePlayListsUseCase,
    private val addPlayListUseCase: AddPlayListUseCase,
    private val deletePlayListUseCase: DeletePlayListUseCase,
    private val setPlaylistUseCase: SetPlaylistUseCase,
    private val uiMapper: UiMapper,
    private val espressoIdlingResource: EspressoIdlingResource?
) :
    BaseViewModel<Event, State, Effect>() {
    init {
        subscribePlaylists()
    }

    override fun createInitialState(): State {
        return State(
            PlayListDialogContract.PlayListDialogState.Idle
        )
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnAddPlaylist -> {
                addPlayList(event.name)
            }

            is Event.OnCancel -> {
                dismiss()
            }

            is Event.OnOk -> {
                setPlaylist(event.items)
            }

            is Event.OnDeletePlaylist -> {
                deletePlayList(event.item)
            }
        }
    }

    private fun deletePlayList(item: ItemUi) {
        viewModelScope.launch {
            val result = deletePlayListUseCase(
                DeletePlayListUseCase.Params(
                    uiMapper.map(item)
                )
            )
            when (result) {
                is DomainResult.Success -> {}

                is DomainResult.Error -> {
                    when (result.exception) {
                        is DomainResult.UnknownException -> {
                            setEffect {
                                Effect.UnknownException
                            }
                        }

                        is PlaylistException.PlaylistsSizeLimit -> {
                            setEffect {
                                Effect.PlaylistsSizeLimit
                            }
                        }
                    }
                }

            }
        }
    }

    private fun subscribePlaylists() {
        espressoIdlingResource?.increment()
        viewModelScope.launch {
            setState {
                copy(
                    playListDialogState = PlayListDialogContract.PlayListDialogState.Loading
                )
            }
            val playLists = subscribePlayListsUseCase()
            espressoIdlingResource?.decrement()
            when (playLists) {
                is DomainResult.Success -> {

                    playLists.data.collect {
                        setState {
                            copy(
                                playListDialogState = PlayListDialogContract.PlayListDialogState.Success(
                                    it.map { item ->
                                        uiMapper.map(item)
                                    }
                                )
                            )
                        }

                    }
                }

                is DomainResult.Error -> TODO()
            }
        }
    }

    private fun setPlaylist(items: List<ItemUi>) {
        espressoIdlingResource?.increment()
        viewModelScope.launch {
            setState {
                copy(
                    playListDialogState = PlayListDialogContract.PlayListDialogState.Loading
                )
            }
            val playLists = setPlaylistUseCase(
                SetPlaylistUseCase.Params(
                   /* items.map {
                        uiMapper.map(it)
                    }*/
                    uiMapper.map(items)
                )
            )
            espressoIdlingResource?.decrement()
            when (playLists) {
                is DomainResult.Success -> {
                    dismiss()
                }

                is DomainResult.Error -> setEffect {
                    Effect.UnknownException
                }
            }
        }
    }

    private fun addPlayList(playListName: String) {
        espressoIdlingResource?.increment()
        viewModelScope.launch {
            setState {
                copy(
                    playListDialogState = PlayListDialogContract.PlayListDialogState.Loading
                )
            }
            val playListsId = addPlayListUseCase(
                AddPlayListUseCase.Params(
                    playListName
                )
            )
            espressoIdlingResource?.decrement()
            when (playListsId) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            playListDialogState = PlayListDialogContract.PlayListDialogState.Idle
                        )
                    }
                }

                is DomainResult.Error -> TODO()
            }
        }
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
        private val deletePlayListUseCase: DeletePlayListUseCase,
        private val setPlaylistUseCase: SetPlaylistUseCase,
        private val uiMapper: UiMapper,
        private val espressoIdlingResource: EspressoIdlingResource?
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PlayListDialogViewModel(
                subscribePlayLists,
                addPlayListUseCase,
                deletePlayListUseCase,
                setPlaylistUseCase,
                uiMapper,
                espressoIdlingResource,
            ) as T
        }
    }
}