package com.yes.alarmclockfeature.domain.usecase

import com.yes.alarmclockfeature.data.repository.AlarmListRepository
import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class SubscribeAlarmsUseCase(
    dispatcher: CoroutineDispatcher,
    private val alarmListRepository: AlarmListRepository,
) : UseCase<Alarm, Flow<List<Alarm>>>(dispatcher) {
    override suspend fun run(params: Alarm?): DomainResult<Flow<List<Alarm>>> {
        return DomainResult.Success(
            alarmListRepository.subscribeAlarms()
        )
    }
}