package com.yes.playlistfeature.presentation.contract

import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState
import com.yes.playlistfeature.presentation.model.TrackUI

class PlaylistContract {
    sealed class Event : UiEvent {

    }

    data class State(
        val playlistState: PlaylistState
    ) : UiState



    sealed class PlaylistState {
        data object Idle : PlaylistState()

        data object Loading : PlaylistState()
        data class Success(val tracks: List<TrackUI>) : PlaylistState() {


        }
    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }
}