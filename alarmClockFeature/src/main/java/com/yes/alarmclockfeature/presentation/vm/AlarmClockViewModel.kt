package com.yes.alarmclockfeature.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yes.alarmclockfeature.presentation.contract.AlarmClockContract
import com.yes.core.presentation.BaseViewModel

class AlarmClockViewModel:BaseViewModel<AlarmClockContract.Event, AlarmClockContract.State, AlarmClockContract.Effect>() {
    override fun createInitialState(): AlarmClockContract.State {
        return AlarmClockContract.State(
            AlarmClockContract.AlarmClockState.Idle
        )
    }

    override fun handleEvent(event: AlarmClockContract.Event) {
        TODO("Not yet implemented")
    }
    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return AlarmClockViewModel() as T
        }
    }
}