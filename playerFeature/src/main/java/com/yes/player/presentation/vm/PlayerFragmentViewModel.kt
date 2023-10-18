package com.yes.player.presentation.vm


import com.yes.core.presentation.BaseViewModel
import com.yes.player.presentation.contract.PlayerFragmentContract.*

class PlayerFragmentViewModel(

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