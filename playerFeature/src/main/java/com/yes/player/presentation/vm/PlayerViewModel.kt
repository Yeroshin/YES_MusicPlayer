package com.yes.player.presentation.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import com.yes.player.domain.usecase.PlayUseCase
import com.yes.player.domain.usecase.SeekToNextUseCase
import com.yes.player.domain.usecase.SeekToPreviousUseCase
import com.yes.player.domain.usecase.SeekUseCase
import com.yes.player.domain.usecase.SubscribeCurrentPlaylistUseCase
import com.yes.player.domain.usecase.SubscribePlayerStateUseCase
import com.yes.player.domain.usecase.SubscribeDurationCounterUseCase
import com.yes.player.domain.usecase.SubscribeVisualizerUseCase
import com.yes.player.presentation.contract.PlayerContract.*
import com.yes.player.presentation.mapper.MapperUI
import com.yes.player.presentation.model.PlayerStateUI
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val mapperUI: MapperUI,
    private val playUseCase: PlayUseCase,
    private val subscribeDurationCounterUseCase: SubscribeDurationCounterUseCase,
    private val seekToNextUseCase: SeekToNextUseCase,
    private val seekToPreviousUseCase: SeekToPreviousUseCase,
    private val subscribeCurrentPlaylistUseCase: SubscribeCurrentPlaylistUseCase,
    private val subscribePlayerStateUseCase: SubscribePlayerStateUseCase,
    private val seekUseCase: SeekUseCase,
    private val subscribeVisualizerUseCase: SubscribeVisualizerUseCase,
) : BaseViewModel<Event, State, Effect>() {

    init {
        subscribeDurationCounter()
        subscribeCurrentPlaylist()
        subscribeCurrentTrack()
        subscribeVisualizer()
    }

    private fun subscribeVisualizer() {
        viewModelScope.launch {
            when (
                val result = subscribeVisualizerUseCase()
            ) {
                is DomainResult.Success -> {
                    result.data.collect {
                        setState {
                            copy(
                                playerState = PlayerState.Success(
                                    PlayerStateUI(
                                        visualizerData = emptyList()
                                    )
                                )
                            )
                        }
                    }
                }
                is DomainResult.Error -> setEffect {
                    Effect.UnknownException
                }
            }
        }
    }

    private fun subscribeCurrentTrack() {
        viewModelScope.launch {

            when (val result = subscribePlayerStateUseCase()) {
                is DomainResult.Success -> {
                    result.data.collect {
                        setState {
                            copy(
                                playerState = PlayerState.Success(
                                    mapperUI.map(it)
                                )
                            )
                        }
                    }
                }

                is DomainResult.Error -> setEffect {
                    Effect.UnknownException
                }
            }
        }
    }

    private fun subscribeCurrentPlaylist() {
        viewModelScope.launch {

            when (val result = subscribeCurrentPlaylistUseCase()) {
                is DomainResult.Success -> {
                    result.data.collect {
                        setState {
                            copy(
                                playerState = PlayerState.Success(
                                    mapperUI.map(it)
                                )
                            )
                        }
                    }
                }

                is DomainResult.Error -> setEffect {
                    Effect.UnknownException
                }
            }
        }
    }

    private fun subscribeDurationCounter() {
        viewModelScope.launch {

            when (val result = subscribeDurationCounterUseCase()) {
                is DomainResult.Success -> {
                    result.data.collect {
                        setState {
                            copy(
                                playerState = PlayerState.Success(
                                    mapperUI.map(it)
                                )
                            )
                        }
                    }
                }

                is DomainResult.Error -> setEffect {
                    Effect.UnknownException
                }
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
                seekToNext()
            }

            is Event.OnSeekToPrevious -> {
                seekToPrevious()
            }

            is Event.OnSeek -> {
                seek(event.position)
            }
        }
    }

    private fun seek(position: Int) {
        viewModelScope.launch {
            val result = seekUseCase(
                SeekUseCase.Params(
                    position.toLong()
                )
            )
            when (result) {
                is DomainResult.Success -> {}
                is DomainResult.Error -> setEffect {
                    Effect.UnknownException
                }
            }
        }
    }

    private fun play() {
        viewModelScope.launch {
          //  subscribeVisualizerUseCase()//tmp!
            val result = playUseCase()
            when (result) {
                is DomainResult.Success -> {}
                is DomainResult.Error -> setEffect {
                    Effect.UnknownException
                }
            }
        }
    }

    private fun seekToNext() {
        viewModelScope.launch {
            val result = seekToNextUseCase()
            when (result) {
                is DomainResult.Success -> {}
                is DomainResult.Error -> setEffect {
                    Effect.UnknownException
                }
            }
        }
    }

    private fun seekToPrevious() {
        viewModelScope.launch {
            val result = seekToPreviousUseCase()
            when (result) {
                is DomainResult.Success -> {}
                is DomainResult.Error -> setEffect {
                    Effect.UnknownException
                }
            }
        }
    }

    class Factory(
        private val mapperUI: MapperUI,
        private val playSynchroUseCase: PlayUseCase,
        private val subscribeDurationCounterUseCase: SubscribeDurationCounterUseCase,
        private val seekToNextUseCase: SeekToNextUseCase,
        private val seekToPreviousUseCase: SeekToPreviousUseCase,
        private val subscribeCurrentPlaylistUseCase: SubscribeCurrentPlaylistUseCase,
        private val subscribePlayerStateUseCase: SubscribePlayerStateUseCase,
        private val seekUseCase: SeekUseCase,
        private val subscribeVisualizerUseCase: SubscribeVisualizerUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PlayerViewModel(
                mapperUI,
                playSynchroUseCase,
                subscribeDurationCounterUseCase,
                seekToNextUseCase,
                seekToPreviousUseCase,
                subscribeCurrentPlaylistUseCase,
                subscribePlayerStateUseCase,
                seekUseCase,
                subscribeVisualizerUseCase,

            ) as T
        }
    }
}