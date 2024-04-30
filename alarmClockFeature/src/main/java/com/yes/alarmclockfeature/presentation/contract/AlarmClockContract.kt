package com.yes.alarmclockfeature.presentation.contract

import com.yes.alarmclockfeature.presentation.model.AlarmUI
import com.yes.alarmclockfeature.presentation.ui.datepicker.DatePickerManager
import com.yes.core.presentation.ui.UiEffect
import com.yes.core.presentation.ui.UiEvent
import com.yes.core.presentation.ui.UiState

class AlarmClockContract {
    sealed class Event : UiEvent {
        data class OnAddAlarm(
            val date: DatePickerManager.Time,
            val selectedDays:Set<Int>
        ) : Event()
        data class OnDeleteAlarm(
            val alarm:AlarmUI
        ) : Event()
        data class OnSetAlarm(
            val alarm:AlarmUI
        ) : Event()
        data class OnAlarmEnabled(
            val alarm:AlarmUI
        ): Event()

    }

    data class State(
        val alarmClockState: AlarmClockState
    ) : UiState


    sealed class AlarmClockState {
        data class Success(val items: List<AlarmUI>):AlarmClockState()
        data object Idle : AlarmClockState()
    }

    sealed class Effect : UiEffect {
        data object UnknownException : Effect()
    }
}