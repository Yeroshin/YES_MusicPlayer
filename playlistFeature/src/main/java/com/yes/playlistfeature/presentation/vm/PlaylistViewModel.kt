package com.yes.playlistfeature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import com.yes.core.util.EspressoIdlingResource
import com.yes.playlistfeature.domain.usecase.ChangeTracksPositionUseCase
import com.yes.playlistfeature.domain.usecase.DeleteTrackUseCase
import com.yes.playlistfeature.domain.usecase.SetModeUseCase
import com.yes.playlistfeature.domain.usecase.SetSettingsTrackIndexUseCase
import com.yes.playlistfeature.domain.usecase.SetTracksToPlayerPlaylistUseCase

import com.yes.playlistfeature.domain.usecase.SubscribeCurrentPlaylistTracksUseCase
import com.yes.playlistfeature.domain.usecase.SubscribePlayerCurrentTrackIndexUseCase
import com.yes.playlistfeature.domain.usecase.PlayTrackUseCase
import com.yes.playlistfeature.presentation.contract.PlaylistContract
import com.yes.playlistfeature.presentation.contract.PlaylistContract.State
import com.yes.playlistfeature.presentation.contract.PlaylistContract.Effect
import com.yes.playlistfeature.presentation.mapper.MapperUI
import com.yes.playlistfeature.presentation.model.TrackUI
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val subscribeCurrentPlaylistTracksUseCase: SubscribeCurrentPlaylistTracksUseCase,
    private val mapperUI: MapperUI,
    private val espressoIdlingResource: EspressoIdlingResource?,
    private val deleteTrackUseCase: DeleteTrackUseCase,
    private val setTracksToPlayerPlaylistUseCase: SetTracksToPlayerPlaylistUseCase,
    private val setModeUseCase: SetModeUseCase,
    private val changeTracksPositionUseCase: ChangeTracksPositionUseCase,
    private val playTrackUseCase: PlayTrackUseCase,
    private val setSettingsTrackIndexUseCase: SetSettingsTrackIndexUseCase,
    private val subscribePlayerCurrentTrackIndexUseCase: SubscribePlayerCurrentTrackIndexUseCase
) : BaseViewModel<PlaylistContract.Event, State, Effect>() {
    init {
        subscribeTracks()
        //  subscribePlayerCurrentTrackIndex()
    }

    override fun createInitialState(): State {
        return State(
            PlaylistContract.PlaylistState.Idle
        )
    }

    override fun handleEvent(event: PlaylistContract.Event) {
        when (event) {
            is PlaylistContract.Event.OnDeleteTrack -> deleteTrack(event.track)
            is PlaylistContract.Event.OnModeChange -> setMode()
            is PlaylistContract.Event.OnMoveItemPosition -> moveItemPosition(event.fromPosition, event.toPosition)
            is PlaylistContract.Event.OnPlayTrack -> playTrack(event.position)
        }
    }

    private fun playTrack(position:Int) {
        viewModelScope.launch {
            val result = playTrackUseCase(
                PlayTrackUseCase.Params(position)
            )
            when (result) {
                is DomainResult.Success -> {
                   // setSettingsTrackIndex(position)
                }

                is DomainResult.Error -> {}
            }
        }
    }

    private fun subscribePlayerCurrentTrackIndex() {
        viewModelScope.launch {
            val result = subscribePlayerCurrentTrackIndexUseCase()
            when (result) {
                is DomainResult.Success -> {
                    result.data.collect {

                        setState {
                            copy(
                                playlistState = PlaylistContract.PlaylistState.CurrentTrack(
                                    it
                                )
                            )
                        }
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    private fun setSettingsTrackIndex(index: Int) {

        viewModelScope.launch {
            val result = setSettingsTrackIndexUseCase(
                SetSettingsTrackIndexUseCase.Params(index)
            )
            when (result) {
                is DomainResult.Success -> {

                }

                is DomainResult.Error -> {}
            }
        }
    }

    /*  private fun subscribeCurrentTrackIndex() {

          viewModelScope.launch {

              val result = subscribeSettingsCurrentTrackIndexUseCase()
              when (result) {
                  is DomainResult.Success -> {
                      result.data.collect {
                          setState {
                              copy(
                                  playlistState = PlaylistContract.PlaylistState.CurrentTrack(
                                      it
                                  )
                              )
                          }
                     }
                  }

                  is DomainResult.Error -> TODO()
              }
          }
      }*/
    private fun moveItemPosition(fromPosition: Int, toPosition: Int) {
        viewModelScope.launch {
            val result = changeTracksPositionUseCase(
                ChangeTracksPositionUseCase.Params(
                    fromPosition,
                    toPosition
                )
            )
            when (result) {
                is DomainResult.Success -> {

                }

                is DomainResult.Error -> {}
            }
        }
    }

    private fun setMode() {
        viewModelScope.launch {
            val result = setModeUseCase()
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            playlistState = PlaylistContract.PlaylistState.Success(
                                mode = mapperUI.map(result.data)
                            )
                        )
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    private fun deleteTrack(trackUI: TrackUI) {
        viewModelScope.launch {
            setState {
                copy(
                    playlistState = PlaylistContract.PlaylistState.Loading
                )
            }
            val result = deleteTrackUseCase(
                DeleteTrackUseCase.Params(
                    mapperUI.map(trackUI)
                )

            )
            espressoIdlingResource?.decrement()
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            playlistState = PlaylistContract.PlaylistState.Idle
                        )
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    private fun subscribeTracks() {
        espressoIdlingResource?.increment()
        viewModelScope.launch {
            setState {
                copy(
                    playlistState = PlaylistContract.PlaylistState.Loading
                )
            }
            val playLists = subscribeCurrentPlaylistTracksUseCase()
            espressoIdlingResource?.decrement()
            when (playLists) {
                is DomainResult.Success -> {

                    playLists.data.collect {
                       /* setTracksToPlayerPlaylistUseCase(
                            SetTracksToPlayerPlaylistUseCase.Params(
                                it
                            )
                        )*/
                        setState {
                            copy(
                                playlistState = PlaylistContract.PlaylistState.Success(
                                    it.map { item ->
                                        mapperUI.map(item)
                                    }
                                )
                            )
                        }
                        subscribePlayerCurrentTrackIndex()
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    class Factory(
        private val espressoIdlingResource: EspressoIdlingResource?,
        private val subscribeCurrentPlaylistTracksUseCase: SubscribeCurrentPlaylistTracksUseCase,
        private val mapperUI: MapperUI,
        private val deleteTrackUseCase: DeleteTrackUseCase,
        private val setTracksToPlayerPlaylistUseCase: SetTracksToPlayerPlaylistUseCase,
        private val setModeUseCase: SetModeUseCase,
        private val changeTracksPositionUseCase: ChangeTracksPositionUseCase,
        private val playTrackUseCase: PlayTrackUseCase,
        private val setSettingsTrackIndexUseCase: SetSettingsTrackIndexUseCase,
        private val subscribePlayerCurrentTrackIndexUseCase: SubscribePlayerCurrentTrackIndexUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PlaylistViewModel(
                subscribeCurrentPlaylistTracksUseCase,
                mapperUI,
                espressoIdlingResource,
                deleteTrackUseCase,
                setTracksToPlayerPlaylistUseCase,
                setModeUseCase,
                changeTracksPositionUseCase,
                playTrackUseCase,
                setSettingsTrackIndexUseCase,
                subscribePlayerCurrentTrackIndexUseCase
            ) as T
        }
    }
}