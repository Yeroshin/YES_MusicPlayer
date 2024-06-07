package com.yes.playlistfeature.presentation.contract


import com.yes.core.presentation.ui.UiEffect
import com.yes.core.presentation.ui.UiEvent
import com.yes.core.presentation.ui.UiState
import com.yes.playlistfeature.presentation.model.TrackUI

class PlaylistContract {
    sealed class Event : UiEvent {
        data class OnMoveItemPosition(val fromPosition:Int,val toPosition:Int): Event()
        data class OnDeleteTrack(val track:TrackUI) : Event()
        data object OnModeChange : Event()
        data class OnPlayTrack(val position:Int):Event()
    }
    data class State(
        val playlistState: PlaylistState,
        val mode:Int?=null,
        val tracks: List<TrackUI>?=null,
        val currentTrack:Int?=null
    ) : UiState
    sealed class PlaylistState {
        data object Idle : PlaylistState()
        data object Loading : PlaylistState()
        data object Success: PlaylistState()


    }
    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }
}