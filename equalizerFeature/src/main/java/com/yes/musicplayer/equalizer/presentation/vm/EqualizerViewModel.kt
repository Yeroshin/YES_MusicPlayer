package com.yes.musicplayer.equalizer.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import com.yes.musicplayer.equalizer.presentation.contract.EqualizerContract
import com.yes.musicplayer.equalizer.presentation.contract.EqualizerContract.*
import kotlinx.coroutines.launch

class EqualizerViewModel (
) : BaseViewModel<Event, State, Effect>() {

    init {

    }



    override fun createInitialState(): State {
        return State(
            EqualizerState.Idle
        )
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnPlay -> {

            }

            is Event.OnSeekToNext -> {

            }

            is Event.OnSeekToPrevious -> {

            }

            is Event.OnSeek -> {

            }
        }
    }

    class Factory(
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EqualizerViewModel(

                ) as T
        }
    }
}