package com.yes.player.presentation.vm

import com.example.musicplayerfeature.common.MusicServiceConnection
import com.yes.core.presentation.BaseViewModel
import com.yes.player.presentation.contract.PlayerFragmentContract.*

class PlayerFragmentViewModel(
    musicServiceConnection: MusicServiceConnection
) : BaseViewModel<Event, State, Effect>() {
    override fun createInitialState(): State {
        return State(
            PlayerFragmentState.Idle
        )
    }

    override fun handleEvent(event: Event) {
        TODO("Not yet implemented")
    }

    /*private val musicServiceConnection = musicServiceConnection.also {
        it.subscribe(mediaId, subscriptionCallback)
    }*/
}