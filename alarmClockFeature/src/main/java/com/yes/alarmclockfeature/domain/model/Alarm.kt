package com.yes.alarmclockfeature.domain.model

import com.yes.alarmclockfeature.presentation.model.DayOfWeek

data class Alarm (
    val id: Long?,
    val timeHour:Int,
    val timeMinute:Int,
    val daysOfWeek: Set<DayOfWeek>,
    val enabled:Boolean
)