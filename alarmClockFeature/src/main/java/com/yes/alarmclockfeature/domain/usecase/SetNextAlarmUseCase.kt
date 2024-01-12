package com.yes.alarmclockfeature.domain.usecase

import com.yes.alarmclockfeature.data.repository.AlarmListRepository
import com.yes.alarmclockfeature.data.repository.AlarmManagerRepository
import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.presentation.model.DayOfWeek
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.Calendar

class SetNextAlarmUseCase(
    dispatcher: CoroutineDispatcher,
    private val alarmListRepository: AlarmListRepository,
    private val alarmManagerRepository: AlarmManagerRepository,
    private val calendar: Calendar
) : UseCase<Any?, Alarm?>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Alarm?> {
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val currentTime = calendar.get(Calendar.HOUR) * 60 + calendar.get(Calendar.MINUTE)
        var nearestAlarm: Alarm? = null
        var nearestTimeDiff = Int.MAX_VALUE
        alarmListRepository.subscribeAlarms().first().filter {
            it.enabled
        }.map { alarm ->
            if(alarm.daysOfWeek == emptySet<DayOfWeek>()){
                alarm.copy(daysOfWeek = setOf(DayOfWeek.entries.first { it.value == currentDayOfWeek }))
            }else{
                alarm
            }
        }.forEach{alarm->
            alarm.daysOfWeek.forEach{dayOfWeek->
                val minutesUntilNext: Int = ((dayOfWeek.value - currentDayOfWeek + 7) % 7) * 24 * 60+(alarm.timeHour*60+alarm.timeMinute)
                val diff = minutesUntilNext - currentTime
                if (diff < nearestTimeDiff) {
                    nearestTimeDiff = diff
                    nearestAlarm=alarm
                }
            }
        }

        ///////////////////////
        return DomainResult.Success(nearestAlarm)

    }
}