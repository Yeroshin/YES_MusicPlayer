package com.yes.alarmclockfeature.data.dataSource

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.yes.alarmclockfeature.presentation.ui.AlarmActivity
import com.yes.alarmclockfeature.presentation.ui.YESBroadcastReceiver
import java.util.Calendar
import java.util.Date

class AlarmDataSource(
    private val context: Context
) {
  //  private var alarmManager: AlarmManager? = null
    private val alarmManager: AlarmManager by lazy {
      context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
    private lateinit var alarmIntent: Intent
    private lateinit var pendingIntent: PendingIntent

    fun setAlarm(hour: Int, minute: Int) {
       // notification(context)
      //  alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        /////////////////////
        alarmIntent = Intent(context, YESBroadcastReceiver::class.java).apply {
            putExtra("hello","world")
            action = "alarm"
        }
        pendingIntent = PendingIntent
            .getBroadcast(
                context,
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        ////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
           // Toast.makeText(context, "canScheduleExactAlarms ERROR!!!", Toast.LENGTH_SHORT).show()
        }
        val calendar = Calendar.getInstance()
        ///////////////////
        val hourt = calendar.get(Calendar.HOUR)
        val mint = calendar.get(Calendar.MINUTE)
        val sec = calendar.get(Calendar.SECOND)
        val mintr = calendar.get(Calendar.MINUTE)
        ////////////////
        // calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.SECOND, sec + 15)
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
            /* alarmManager.setExactAndAllowWhileIdle(
                 AlarmManager.RTC_WAKEUP,
                 calendar.timeInMillis,
                 pendingIntent
             )*/
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(
                    calendar.timeInMillis,
                    null
                ),
                pendingIntent
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
        ///////////////////////

    }
    fun notification(context: Context){
        val intent=Intent(context, AlarmActivity::class.java).apply {
            flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK /*or
                FLAG_INCLUDE_STOPPED_PACKAGES*/
        }
        val pendingIntent= PendingIntent.getActivity(context,0,intent,0)
        val builder= NotificationCompat.Builder(context,"yes")
            .setSmallIcon(com.yes.coreui.R.drawable.app_icon)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setContentIntent(pendingIntent)

        val notificationManager=
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(123,builder.build())
    }
}