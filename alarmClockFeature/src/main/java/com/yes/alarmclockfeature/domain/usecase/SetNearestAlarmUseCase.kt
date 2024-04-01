package com.yes.alarmclockfeature.domain.usecase

import com.yes.alarmclockfeature.data.repository.AlarmListRepository
import com.yes.alarmclockfeature.data.repository.AlarmManagerRepository
import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.util.CalendarFactory
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import java.util.Calendar

class SetNearestAlarmUseCase(
    dispatcher: CoroutineDispatcher,
    private val alarmListRepository: AlarmListRepository,
    private val alarmManagerRepository: AlarmManagerRepository,
    private val calendarFactory: CalendarFactory
) : UseCase<Any?, Alarm?>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Alarm?> {
        val calendar=calendarFactory.getCalendar()
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        var nearestAlarm: Alarm? = null
        var nearestTimeDiff = Int.MAX_VALUE
        var nearestDayOfWeek = Int.MAX_VALUE
        var diff: Int
        var dayOfAlarm: Int
        alarmListRepository.subscribeAlarms().first().filter {
            it.enabled
        }.mapTo(mutableListOf()) { alarm ->
            if (alarm.daysOfWeek == emptySet<Int>()) {
                alarm.copy(daysOfWeek = setOf(currentDayOfWeek))
            } else {
                alarm
            }
        }.forEach { alarm ->
            alarm.daysOfWeek.forEach { dayOfWeek ->
                val minutesUntilNext = calculateMinutesUntilNextAlarm(
                    dayOfWeek,
                    currentDayOfWeek,
                    alarm.timeHour,
                    alarm.timeMinute,
                    calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)+1//+1 means send next minute after present

                )
                if (minutesUntilNext < 0) {
                    diff = minutesUntilNext + (24 * 60)
                    dayOfAlarm = if (dayOfWeek==Calendar.SATURDAY){
                        Calendar.SUNDAY
                    }else{
                        dayOfWeek+1
                    }
                } else {
                    diff =minutesUntilNext
                    dayOfAlarm=dayOfWeek
                }
                if (diff < nearestTimeDiff) {
                    nearestTimeDiff = diff
                    nearestAlarm = alarm
                    nearestDayOfWeek = dayOfAlarm
                }
            }
        }
        nearestAlarm?.let {
            alarmManagerRepository.setAlarm(it, dayOfWeek = nearestDayOfWeek)
        }?: alarmManagerRepository.cancelAlarm()

        ///////////////////////
        return DomainResult.Success(nearestAlarm)

    }

    private fun calculateMinutesUntilNextAlarm(
        dayOfWeek: Int,
        currentDayOfWeek: Int,
        timeHour: Int,
        timeMinute: Int,
        currentTimeMinutes: Int
    ): Int {
        val daysDiff = (dayOfWeek - currentDayOfWeek + 7) % 7
        return daysDiff * 24 * 60 + (timeHour * 60 + timeMinute) - currentTimeMinutes
    }
}