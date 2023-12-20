package com.yes.alarmclockfeature.domain.usecase

import com.yes.alarmclockfeature.data.repository.AlarmListRepository
import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import kotlinx.coroutines.CoroutineDispatcher

class DeleteAlarmUseCase (
    dispatcher: CoroutineDispatcher,
    private val alarmListRepository: AlarmListRepository,
) : UseCase<Alarm, Boolean>(dispatcher) {
    override suspend fun run(alarm: Alarm?): DomainResult<Boolean> {
        return alarm?.let {
            alarmListRepository.deleteAlarm(alarm)
            DomainResult.Success(true)
        } ?: DomainResult.Error(DomainResult.UnknownException)
    }
}