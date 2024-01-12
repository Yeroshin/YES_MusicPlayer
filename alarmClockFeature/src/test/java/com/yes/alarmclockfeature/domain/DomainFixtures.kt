package com.yes.alarmclockfeature.domain

import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.presentation.model.DayOfWeek
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar

object DomainFixtures {
    private val alarms = listOf(
        Alarm(
            1,
            14,
            30,
            setOf(DayOfWeek.SUNDAY),
            true
        ),
        Alarm(
            2,
            14,
            30,
            setOf(),
            true
        ),
        Alarm(
            3,
            14,
            15,
            setOf(),
            true
        ),
        Alarm(
            3,
            14,
            30,
            setOf(DayOfWeek.WEDNESDAY),
            false
        ),
        Alarm(
            3,
            2,
            30,
            setOf(DayOfWeek.THURSDAY),
            false
        ),
        Alarm(
            3,
            2,
            30,
            setOf(DayOfWeek.FRIDAY),
            true
        ),
        Alarm(
            3,
            2,
            30,
            setOf(DayOfWeek.SATURDAY),
            false
        ),
        Alarm(
            3,
            1,
            30,
            setOf(DayOfWeek.SATURDAY),
            false
        )
    )

    fun getCurrentDay(): Int {
        return Calendar.FRIDAY
    }

    fun getNextAlarm(): Alarm {
        return alarms[5]
    }

    fun getAlarms(): Flow<List<Alarm>> = flow {
        emit(alarms)
    }
}