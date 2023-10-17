package com.yes.playlistfeature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import com.yes.core.util.EspressoIdlingResource
import com.yes.playlistfeature.domain.usecase.DeleteTrackUseCase

import com.yes.playlistfeature.domain.usecase.SubscribeCurrentPlaylistTracksUseCase
import com.yes.playlistfeature.presentation.contract.PlaylistContract
import com.yes.playlistfeature.presentation.contract.PlaylistContract.State
import com.yes.playlistfeature.presentation.contract.PlaylistContract.Effect
import com.yes.playlistfeature.presentation.mapper.Mapper
import com.yes.playlistfeature.presentation.model.TrackUI
import kotlinx.coroutines.launch
import java.util.ArrayDeque

class PlaylistViewModel(
    private val subscribeCurrentPlaylistTracksUseCase: SubscribeCurrentPlaylistTracksUseCase,
    private val mapper: Mapper,
    private val espressoIdlingResource: EspressoIdlingResource?,
    private val deleteTrackUseCase: DeleteTrackUseCase
) : BaseViewModel<PlaylistContract.Event, State, Effect>() {
    init {
        subscribeTracks()
    }

    override fun createInitialState(): State {
        return State(
            PlaylistContract.PlaylistState.Idle
        )
    }

    override fun handleEvent(event: PlaylistContract.Event) {
        when(event){
            is PlaylistContract.Event.OnDeleteTrack->{
                deleteTrack(event.track)
            }
        }
    }
    private fun deleteTrack(trackUI: TrackUI){
        viewModelScope.launch {
            setState {
                copy(
                    playlistState = PlaylistContract.PlaylistState.Loading
                )
            }
            val result = deleteTrackUseCase(
                DeleteTrackUseCase.Params(
                    mapper.map(trackUI)
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
                is DomainResult.Error->{}
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
                        setState {
                            copy(
                                playlistState = PlaylistContract.PlaylistState.Success(
                                    it.map { item ->
                                        mapper.map(item)
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

    class Factory(
        private val espressoIdlingResource: EspressoIdlingResource?,
        private val subscribeCurrentPlaylistTracksUseCase: SubscribeCurrentPlaylistTracksUseCase,
        private val mapper: Mapper,
        private val deleteTrackUseCase: DeleteTrackUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PlaylistViewModel(
                subscribeCurrentPlaylistTracksUseCase,
                mapper,
                espressoIdlingResource,
                deleteTrackUseCase
                ) as T
        }
    }
}