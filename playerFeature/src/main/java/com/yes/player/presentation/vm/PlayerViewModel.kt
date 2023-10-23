package com.yes.player.presentation.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import com.yes.player.domain.usecase.PlayUseCase
import com.yes.player.domain.usecase.SeekToNextUseCase
import com.yes.player.domain.usecase.SeekToPreviousUseCase
import com.yes.player.domain.usecase.SubscribeDurationCounterUseCase
import com.yes.player.presentation.contract.PlayerContract.*
import com.yes.player.presentation.mapper.MapperUI
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val mapperUI: MapperUI,
    private val playUseCase: PlayUseCase,
    private val subscribeDurationCounterUseCase: SubscribeDurationCounterUseCase,
    private val seekToNextUseCase: SeekToNextUseCase,
    private val seekToPreviousUseCase: SeekToPreviousUseCase
) : BaseViewModel<Event, State, Effect>() {
    private var job: Job? = null

    init {
        subscribeDurationCounter()
    }

    private fun subscribePlayList() {

    }

    private fun subscribeDurationCounter() {
        viewModelScope.launch {

            val result = subscribeDurationCounterUseCase()

            when (result) {
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
                seekToNext()
            }

            is Event.OnSeekToPrevious -> {
                seekToPrevious()
            }

            is Event.OnSeek -> {

            }
        }
    }

    private fun play() {
        viewModelScope.launch {
            val result = playUseCase()
            when (result) {
                is DomainResult.Success -> {}
                is DomainResult.Error -> TODO()
            }
        }
    }

    private fun seekToNext() {
        viewModelScope.launch {
            val result = seekToNextUseCase()
            when (result) {
                is DomainResult.Success -> {}
                is DomainResult.Error -> TODO()
            }
        }
    }

    private fun seekToPrevious() {
        viewModelScope.launch {
            val result = seekToPreviousUseCase()
            when (result) {
                is DomainResult.Success -> {}
                is DomainResult.Error -> TODO()
            }
        }
    }

    class Factory(
        private val mapperUI: MapperUI,
        private val playSynchroUseCase: PlayUseCase,
        private val subscribeDurationCounterUseCase: SubscribeDurationCounterUseCase,
        private val seekToNextUseCase: SeekToNextUseCase,
        private val seekToPreviousUseCase: SeekToPreviousUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return PlayerViewModel(
                mapperUI,
                playSynchroUseCase,
                subscribeDurationCounterUseCase,
                seekToNextUseCase,
                seekToPreviousUseCase
            ) as T
        }
    }
}