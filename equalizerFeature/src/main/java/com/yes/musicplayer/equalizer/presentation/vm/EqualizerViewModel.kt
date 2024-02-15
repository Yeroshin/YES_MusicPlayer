package com.yes.musicplayer.equalizer.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import com.yes.musicplayer.equalizer.domain.usecase.GetEqualizerUseCase
import com.yes.musicplayer.equalizer.presentation.contract.EqualizerContract.*
import com.yes.musicplayer.equalizer.presentation.mapper.MapperUI
import kotlinx.coroutines.launch

class EqualizerViewModel (
    private val getEqualizerUseCase: GetEqualizerUseCase,
    private val mapperUI: MapperUI
) : BaseViewModel<Event, State, Effect>() {

    init {
        viewModelScope.launch {
            val result = getEqualizerUseCase()
            when (result) {
                is DomainResult.Success -> {
                    setState {
                        copy(
                            state =EqualizerState.Success(
                                mapperUI.map(result.data )
                            )
                        )
                    }
                }

                is DomainResult.Error -> {}
            }
        }
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

            is Event.OnSeek -> {

            }
        }
    }

    class Factory(
        private val getEqualizerUseCase: GetEqualizerUseCase,
        private val mapperUI: MapperUI
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return EqualizerViewModel(
                getEqualizerUseCase,
                mapperUI
                ) as T
        }
    }
}