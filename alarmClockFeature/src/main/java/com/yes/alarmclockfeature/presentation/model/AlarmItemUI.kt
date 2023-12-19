package com.yes.alarmclockfeature.presentation.model

data class AlarmItemUI (
    val alarmTime:String,
    val alarmTimeLeft:String="",
    val sun:Boolean=false,
    val mon:Boolean=false,
    val tue:Boolean=false,
    val wed:Boolean=false,
    val thu:Boolean=false,
    val fri:Boolean=false,
    val sat:Boolean=false,
    val enabled:Boolean=true
)