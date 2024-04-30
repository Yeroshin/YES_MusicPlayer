package com.yes.core.presentation.ui

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IBaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect>  {
    val uiState: StateFlow<State>
    val effect: Flow<Effect>
    fun  setEvent(event: Event)
}