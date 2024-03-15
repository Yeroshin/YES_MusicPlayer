package com.yes.alarmclockfeature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yes.alarmclockfeature.domain.usecase.AddAlarmUseCase
import com.yes.alarmclockfeature.domain.usecase.DeleteAlarmUseCase
import com.yes.alarmclockfeature.domain.usecase.SetAlarmUseCase
import com.yes.alarmclockfeature.domain.usecase.SetNearestAlarmUseCase
import com.yes.alarmclockfeature.domain.usecase.SubscribeAlarmsUseCase
import com.yes.alarmclockfeature.presentation.contract.AlarmClockContract.*
import com.yes.alarmclockfeature.presentation.mapper.MapperUI
import com.yes.alarmclockfeature.presentation.model.AlarmUI
import com.yes.alarmclockfeature.presentation.ui.datepicker.DatePickerManager
import com.yes.core.domain.models.DomainResult
import com.yes.core.presentation.BaseViewModel
import kotlinx.coroutines.launch

class AlarmClockViewModel(
    private val mapper: MapperUI,
    private val addAlarmUseCase: AddAlarmUseCase,
    private val subscribeAlarmsUseCase: SubscribeAlarmsUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase,
    private val setAlarmUseCase: SetAlarmUseCase,
    private val setNearestAlarmUseCase: SetNearestAlarmUseCase
) : BaseViewModel<Event, State, Effect>() {
    init {
        viewModelScope.launch {
            val result = subscribeAlarmsUseCase()
            when (result) {
                is DomainResult.Success -> {
                    result.data.collect {
                        setState {
                            copy(
                                alarmClockState = AlarmClockState.Success(
                                    it.map { item ->
                                        mapper.map(item)
                                    }
                                )
                            )
                        }
                    }
                }

                is DomainResult.Error -> {}
            }
        }
    }

    override fun createInitialState(): State {
        return State(
            AlarmClockState.Idle
        )
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnAddAlarm -> addAlarm(event.date, event.selectedDays)
            is Event.OnDeleteAlarm -> deleteAlarm(event.alarm)
            is Event.OnSetAlarm -> setAlarm(event.alarm)
            is Event.OnAlarmEnabled -> setAlarm(event.alarm)
        }
    }


    private fun setAlarm(alarm: AlarmUI) {
       /* viewModelScope.launch {
            val result = setAlarmUseCase(
                mapper.map(alarm)
            )
            when (result) {
                is DomainResult.Success -> {
                    setNearestAlarmUseCase()
                }
                is DomainResult.Error -> {}
            }
        }*/
    }

    private fun deleteAlarm(alarm: AlarmUI) {
        viewModelScope.launch {
            val result = deleteAlarmUseCase(
                mapper.map(alarm)
            )
            when (result) {
                is DomainResult.Success -> {
                    setNearestAlarmUseCase()
                }
                is DomainResult.Error -> {}
            }
        }
    }

    private fun addAlarm(date: DatePickerManager.Time, selectedDays: Set<Int>) {
        viewModelScope.launch {
            val result = addAlarmUseCase(
                mapper.map(date, selectedDays)
            )
            when (result) {
                is DomainResult.Success -> {
                    setNearestAlarmUseCase()
                }
                is DomainResult.Error -> {}
            }
        }
    }

    class Factory(
        private val mapper: MapperUI,
        private val addAlarmUseCase: AddAlarmUseCase,
        private val subscribeAlarmsUseCase: SubscribeAlarmsUseCase,
        private val deleteAlarmUseCase: DeleteAlarmUseCase,
        private val setAlarmUseCase: SetAlarmUseCase,
        private val setNearestAlarmUseCase: SetNearestAlarmUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AlarmClockViewModel(
                mapper,
                addAlarmUseCase,
                subscribeAlarmsUseCase,
                deleteAlarmUseCase,
                setAlarmUseCase,
                setNearestAlarmUseCase
            ) as T
        }
    }
}