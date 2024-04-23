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
import com.yes.alarmclockfeature.presentation.model.DayOfWeek
import com.yes.alarmclockfeature.presentation.ui.AlarmActivity
import com.yes.alarmclockfeature.presentation.ui.YESBroadcastReceiver
import java.util.Calendar
import java.util.Date

class AlarmDataSource(
    private val context: Context,
    private val calendar: Calendar,
    private val alarmManager: AlarmManager
) {
    //  private var alarmManager: AlarmManager? = null
    /* private val alarmManager: AlarmManager by lazy {
       context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
     }*/
    private val alarmIntent = Intent(context, YESBroadcastReceiver::class.java)
    private val pendingIntent = PendingIntent
        .getBroadcast(
            context,
            0,
            alarmIntent,
            PendingIntent.FLAG_IMMUTABLE//PendingIntent.FLAG_UPDATE_CURRENT//
        )

    fun cancelAlarm() {
        alarmManager.cancel(pendingIntent)
    }

    fun setAlarm(alarmId:Long?,hour: Int, minute: Int, dayOfWeek: Int) {
        // notification(context)
        //  alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        /////////////////////
        val alarmIntent = Intent(context, YESBroadcastReceiver::class.java)
        alarmIntent.putExtra("alarmId",alarmId)
        val pendingIntent = PendingIntent
            .getBroadcast(
                context,
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            //PendingIntent.FLAG_IMMUTABLE
            )


        ////////////////////
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val can = alarmManager.canScheduleExactAlarms()
            if (!can) {
                Toast.makeText(context, "canScheduleExactAlarms ERROR!!!", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            //   Toast.makeText(context, "canScheduleExactAlarms ERROR!!!", Toast.LENGTH_SHORT).show()
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)
        calendar.set(Calendar.SECOND, 0)
        alarmManager.cancel(pendingIntent)
        ///////////////////////
        //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
    }

    fun notification(context: Context) {
        val intent = Intent(context, AlarmActivity::class.java).apply {
            flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK /*or
                FLAG_INCLUDE_STOPPED_PACKAGES*/
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val builder = NotificationCompat.Builder(context, "yes")
            .setSmallIcon(com.yes.coreui.R.drawable.app_icon)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setContentIntent(pendingIntent)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(123, builder.build())
    }
}
//testing commands
//E:\androidsdk\platform-tools>
//adb shell dumpsys alarm
//adb shell dumpsys deviceidle step
//adb shell dumpsys deviceidle -h //all doze commands
// adb shell dumpsys battery unplug
//////////////////////////////////////
//There may be a condition which prevents the phone
// from going into IDLE, such as a scheduled alarm clock.
// Make sure there are no alarm clock apps set to go off in
// less than an hour of you trying to force the phone into IDLE.