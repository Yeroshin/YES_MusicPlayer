package com.yes.alarmclockfeature.data.dataSource

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.yes.alarmclockfeature.presentation.ui.YESBroadcastReceiver
import java.util.Calendar

class AlarmDataSource(
private val context: Context
) {
    private var alarmManager: AlarmManager? = null
    private lateinit var alarmIntent: Intent
    private lateinit var pendingIntent:PendingIntent

    fun setAlarm(hour:Int,minute:Int){

        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, YESBroadcastReceiver::class.java)
        pendingIntent= PendingIntent.getBroadcast(context, 0, alarmIntent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager?.canScheduleExactAlarms()
        } else {
            true
        }


        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
       /* alarmManager?.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                calendar.timeInMillis,
                pendingIntent
            ),
            pendingIntent
        )*/
        alarmManager?.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager?.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
            val next=alarmManager?.nextAlarmClock
            Log.d("alarm","loaded!")
        }

    }
}