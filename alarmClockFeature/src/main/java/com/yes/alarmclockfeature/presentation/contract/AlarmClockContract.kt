package com.yes.alarmclockfeature.presentation.contract

import com.yes.alarmclockfeature.presentation.model.AlarmItemUI
import com.yes.alarmclockfeature.presentation.ui.datepicker.DatePickerManager
import com.yes.core.presentation.UiEffect
import com.yes.core.presentation.UiEvent
import com.yes.core.presentation.UiState

class AlarmClockContract {
    sealed class Event : UiEvent {
        data class OnAddAlarm(
            val date: DatePickerManager.Time,
            val repeating:Map<String,Boolean>
        ) : Event()

    }

    data class State(
        val alarmClockState: AlarmClockState
    ) : UiState


    sealed class AlarmClockState {
        data class Success(val info: AlarmItemUI):AlarmClockState()
        data object Idle : AlarmClockState()
    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }
}