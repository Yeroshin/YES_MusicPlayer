package com.yes.alarmclockfeature.domain.usecase

import com.yes.alarmclockfeature.data.repository.AlarmListRepository
import com.yes.alarmclockfeature.data.repository.AlarmManagerRepository
import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import java.util.Calendar

class SetNearestAlarmUseCase(
    dispatcher: CoroutineDispatcher,
    private val alarmListRepository: AlarmListRepository,
    private val alarmManagerRepository: AlarmManagerRepository,
    private val calendar: Calendar
) : UseCase<Any?, Alarm?>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Alarm?> {
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val currentTimeMinutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
        var nearestAlarm: Alarm? = null
        var nearestTimeDiff = Int.MAX_VALUE
        var nearestDayOfWeek= Int.MAX_VALUE
        alarmListRepository.subscribeAlarms().first().filter {
            it.enabled
        }.mapTo(mutableListOf()) { alarm ->
            if(alarm.daysOfWeek == emptySet<Int>()){
                alarm.copy(daysOfWeek = setOf(currentDayOfWeek))
            }else{
                alarm
            }
        }.forEach{alarm->
            alarm.daysOfWeek.forEach{dayOfWeek->
             //   val minutesUntilNext: Int = ((dayOfWeek - currentDayOfWeek + 7) % 7) * 24 * 60+(alarm.timeHour*60+alarm.timeMinute)
                val minutesUntilNext = calculateMinutesUntilNextAlarm(
                    dayOfWeek,
                    currentDayOfWeek,
                    alarm.timeHour,
                    alarm.timeMinute,
                    currentTimeMinutes
                )
             //   val diff = minutesUntilNext - currentTimeMinutes
                val diff = if (minutesUntilNext < 0){
                    minutesUntilNext + 24 * 60
                } else minutesUntilNext
                if (diff < nearestTimeDiff) {
                    nearestTimeDiff = diff
                    nearestAlarm=alarm
                    nearestDayOfWeek=dayOfWeek
                }
            }
        }
        nearestAlarm?.let {
            alarmManagerRepository.setAlarm(it, dayOfWeek=nearestDayOfWeek)
        }
        ///////////////////////
        return DomainResult.Success(nearestAlarm)

    }
    private fun calculateMinutesUntilNextAlarm(dayOfWeek: Int, currentDayOfWeek: Int, timeHour: Int, timeMinute: Int, currentTimeMinutes: Int): Int {
        val daysDiff = (dayOfWeek - currentDayOfWeek + 7) % 7
        return daysDiff * 24 * 60 + (timeHour * 60 + timeMinute) - currentTimeMinutes
    }
}