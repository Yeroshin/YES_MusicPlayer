package com.yes.alarmclockfeature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.alarmclockfeature.domain.usecase.AddAlarmUseCase
import com.yes.alarmclockfeature.presentation.contract.AlarmClockContract
import com.yes.alarmclockfeature.presentation.mapper.MapperUI
import com.yes.alarmclockfeature.presentation.ui.datepicker.DatePickerManager
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import kotlinx.coroutines.launch

class AlarmClockViewModel(
    private val mapper: MapperUI,
    private val addAlarmUseCase: AddAlarmUseCase
) : BaseViewModel<AlarmClockContract.Event, AlarmClockContract.State, AlarmClockContract.Effect>() {
    override fun createInitialState(): AlarmClockContract.State {
        return AlarmClockContract.State(
            AlarmClockContract.AlarmClockState.Idle
        )
    }

    override fun handleEvent(event: AlarmClockContract.Event) {
        when (event) {
            is AlarmClockContract.Event.OnAddAlarm -> {
                addAlarm(event.date, event.repeating)
            }
        }
    }

    private fun addAlarm(date: DatePickerManager.Time, repeating: Map<String, Boolean>) {
        viewModelScope.launch {
            val result = addAlarmUseCase(
                mapper.map(date, repeating)
            )
            when (result) {
                is DomainResult.Success -> {}
                is DomainResult.Error -> {}
            }
        }
    }

    class Factory(
        private val mapper: MapperUI,
        private val addAlarmUseCase: AddAlarmUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AlarmClockViewModel(
                mapper,
                addAlarmUseCase
            ) as T
        }
    }
}