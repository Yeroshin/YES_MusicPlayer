package com.yes.alarmclockfeature.domain.model

data class Alarm (
    val timeHour:Int,
    val timeMinute:Int,
    val sun:Boolean,
    val mon:Boolean,
    val tue:Boolean,
    val wed:Boolean,
    val thu:Boolean,
    val fri:Boolean,
    val sat:Boolean,
    val enabled:Boolean
)