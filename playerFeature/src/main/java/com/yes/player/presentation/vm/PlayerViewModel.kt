package com.yes.player.presentation.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import com.yes.player.domain.usecase.PlayUseCase
import com.yes.player.domain.usecase.SubscribeDurationCounterUseCase
import com.yes.player.presentation.contract.PlayerContract.*
import com.yes.player.presentation.mapper.MapperUI
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val mapperUI: MapperUI,
    private val playUseCase: PlayUseCase,
    private val subscribeDurationCounterUseCase: SubscribeDurationCounterUseCase
) : BaseViewModel<Event, State, Effect>() {
    init {
       // subscribePlaylist()
    }
    private fun subscribePlaylist(){
        viewModelScope.launch {

            val result = subscribeDurationCounterUseCase()

            when (result) {
                is DomainResult.Success -> {
                    result.data.collect{
                        setState {
                            copy(
                                playerState = PlayerState.Success(
                                    mapperUI.map(it)
                                )
                            )
                        }
                    }
                }
                is DomainResult.Error -> TODO()
            }


        }
    }

    override fun createInitialState(): State {
        return State(
            PlayerState.Idle
        )
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnPlay -> {
                play()
            }

            is Event.OnSeekToNext -> {

            }

            is Event.OnSeekToPrevious -> {

            }

            is Event.OnSeek -> {

            }
        }
    }

    private fun play() {
        playUseCase()
        subscribePlaylist()
    }

    class Factory(
        private val mapperUI: MapperUI,
        private val playSynchroUseCase: PlayUseCase,
        private val subscribeDurationCounterUseCase: SubscribeDurationCounterUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PlayerViewModel(
                mapperUI,
                playSynchroUseCase,
                subscribeDurationCounterUseCase
            ) as T
        }
    }
}