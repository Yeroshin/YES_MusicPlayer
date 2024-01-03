package com.yes.alarmclockfeature.data.dataSource

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.yes.alarmclockfeature.presentation.ui.YESBroadcastReceiver
import java.util.Calendar
import java.util.Date

class AlarmDataSource(
    private val context: Context
) {
    private var alarmManager: AlarmManager? = null
    private lateinit var alarmIntent: Intent
    private lateinit var pendingIntent: PendingIntent

    fun setAlarm(hour: Int, minute: Int) {

        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        /////////////////////
        alarmIntent = Intent(context, YESBroadcastReceiver::class.java).apply {
            putExtra("hello","world")
            action = "alarm"
        }
      //  alarmIntent.setAction("alarm")
        pendingIntent = PendingIntent
            .getBroadcast(
                context,
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        ////////////////////

        ///////////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager?.canScheduleExactAlarms()
        } else {
           // Toast.makeText(context, "canScheduleExactAlarms ERROR!!!", Toast.LENGTH_SHORT).show()
        }
        val calendar = Calendar.getInstance()
        /* var currentTime = calendar.timeInMillis
         calendar.set(Calendar.MILLISECOND, currentTime.toInt())*/
        ///////////////////
        val hourt = calendar.get(Calendar.HOUR)
        val mint = calendar.get(Calendar.MINUTE)
        val sec = calendar.get(Calendar.SECOND)
        val mintr = calendar.get(Calendar.MINUTE)
        ////////////////
        // calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.SECOND, sec + 10)
        /* alarmManager?.setAlarmClock(
             AlarmManager.AlarmClockInfo(
                 calendar.timeInMillis,
                 pendingIntent
             ),
             pendingIntent
         )*/
        //     alarmManager?.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             alarmManager?.setExactAndAllowWhileIdle(
                 AlarmManager.RTC_WAKEUP,
                 calendar.timeInMillis,
                 pendingIntent
             )
             val next=alarmManager?.nextAlarmClock
             Log.d("alarm","loaded!")
         }*/

        ///////////////////////
        if (Build.VERSION.SDK_INT >= 23) {
            /* alarmManager?.setExactAndAllowWhileIdle(
                 AlarmManager.RTC_WAKEUP,
                 calendar.timeInMillis,
                 pendingIntent
             )*/
            alarmManager?.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                    calendar.timeInMillis,
                    null
                ), pendingIntent
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager?.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            alarmManager?.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
        ///////////////////////

    }
}