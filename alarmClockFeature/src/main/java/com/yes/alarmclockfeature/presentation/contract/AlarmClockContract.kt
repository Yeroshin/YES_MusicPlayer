package com.yes.alarmclockfeature.presentation.contract

import com.yes.alarmclockfeature.presentation.model.AlarmClockUI
import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState

class AlarmClockContract {
    sealed class Event : UiEvent {
       // data class OnSeek(val position:Int) : Event()

    }

    data class State(
        val alarmClockState: AlarmClockState
    ) : UiState


    sealed class AlarmClockState {
        data class Success(val info: AlarmClockUI):AlarmClockState()
        data object Idle : AlarmClockState()
    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }
}