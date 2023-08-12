package com.yes.core.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IBaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect>  {
    val uiState: StateFlow<State>
    val effect: Flow<Effect>
    fun  setEvent(event: Event)
}