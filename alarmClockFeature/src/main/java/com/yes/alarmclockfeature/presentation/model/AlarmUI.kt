package com.yes.alarmclockfeature.presentation.model

data class AlarmUI (
    val id: Long?,
    val alarmTime:String,
    val alarmTimeLeft:String="",
    val daysOfWeek: Set<DayOfWeek> ,
    val enabled:Boolean=true
)
enum class DayOfWeek {
    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
}