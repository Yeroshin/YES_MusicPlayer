package com.yes.alarmclockfeature.domain.usecase

import com.yes.alarmclockfeature.data.repository.AlarmListRepository
import com.yes.alarmclockfeature.data.repository.AlarmManagerRepository
import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class AddAlarmUseCase (
    dispatcher: CoroutineDispatcher,
    private val alarmListRepository: AlarmListRepository,
    private val alarmManagerRepository: AlarmManagerRepository
) : UseCase<Alarm, Boolean>(dispatcher) {
    override suspend fun run(alarm: Alarm?): DomainResult<Boolean> {
        return alarm?.let {
            alarmListRepository.addAlarm(alarm)
            alarmManagerRepository.setAlarm(alarm)
            DomainResult.Success(true)
        } ?: DomainResult.Error(DomainResult.UnknownException)
    }
}