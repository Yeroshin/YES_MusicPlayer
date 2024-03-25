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
       //////////////
        val tmp =currentTimeMinutes
       // calendar.clear()
        val currentTimeMinutes2 = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
        val tmp2=currentTimeMinutes2
        val tmp3=tmp+tmp2
        /////////////////////
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
                //   val minutesUntilNext: Int = ((dayOfWeek - currentDayOfWeek + 7) % 7) * 24 * 60+(alarm.timeHour*60+alarm.timeMinute)
                val minutesUntilNext = calculateMinutesUntilNextAlarm(
                    dayOfWeek,
                    currentDayOfWeek,
                    alarm.timeHour,
                    alarm.timeMinute,
                    calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)

                )
                //   val diff = minutesUntilNext - currentTimeMinutes
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
        }?:run(
            alarmManagerRepository.cancelAlarm()
        )
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
        /////////////
        val t=calendar.get(Calendar.MINUTE)
        val h=currentTimeMinutes/60
        val m=currentTimeMinutes%60
        val c=Calendar.getInstance()
        val ct=c.get(Calendar.HOUR_OF_DAY) * 60 + c.get(Calendar.MINUTE)
        val nh=ct/60
        val nm=ct%60
        /////////////
        val daysDiff = (dayOfWeek - currentDayOfWeek + 7) % 7
        return daysDiff * 24 * 60 + (timeHour * 60 + timeMinute) - currentTimeMinutes
    }
}