package com.yes.alarmclockfeature.presentation.model

data class AlarmUI (
    val id: Long?,
    val alarmTime:String,
    val alarmTimeLeft:String="",
    val daysOfWeek: Set<DayOfWeek> ,
    val enabled:Boolean=true
)
enum class DayOfWeek(val value: Int) {
    SUNDAY(1), MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(7)
}