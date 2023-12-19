package com.yes.alarmclockfeature.data.mapper

import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.core.data.entity.AlarmDataBaseEntity

class Mapper {
    fun map(alarm: Alarm): AlarmDataBaseEntity {
        return AlarmDataBaseEntity(
            null,
            alarm.timeHour,
            alarm.timeMinute,
            alarm.sun,
            alarm.mon,
            alarm.tue,
            alarm.wed,
            alarm.thu,
            alarm.fri,
            alarm.sat,
            alarm.enabled,
        )
    }
    fun map(alarm:AlarmDataBaseEntity ): Alarm {
        return Alarm(
            alarm.hour,
            alarm.minute,
            alarm.sun,
            alarm.mon,
            alarm.tue,
            alarm.wed,
            alarm.thu,
            alarm.fri,
            alarm.sat,
            alarm.enabled,
        )
    }
}