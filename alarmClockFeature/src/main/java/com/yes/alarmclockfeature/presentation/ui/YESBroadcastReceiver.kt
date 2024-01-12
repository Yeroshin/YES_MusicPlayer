package com.yes.alarmclockfeature.presentation.ui

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.media3.session.MediaController
import com.yes.alarmclockfeature.di.components.AlarmClockComponent
import com.yes.alarmclockfeature.domain.usecase.SetTracksToPlayerPlaylistUseCase
import com.yes.core.data.dataSource.PlayerDataSource
import com.yes.core.di.component.BaseComponent
import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class YESBroadcastReceiver : BroadcastReceiver(), PlayerDataSource.MediaControllerListener {
    private lateinit var component: BaseComponent

    private val getCurrentPlaylistTracksUseCase by lazy {
        (component as AlarmClockComponent).getGetCurrentPlaylistTracksUseCase()
    }
    private val setTracksToPlayerPlaylistUseCase by lazy {
        (component as AlarmClockComponent).getSetTracksToPlayerPlaylistUseCase()
    }
    private val activityClass by lazy {
        (component as AlarmClockComponent).getActivity()
    }

    private lateinit var context: Context
    override fun onReceive(context: Context, intent: Intent) {
        this.context = context.applicationContext
        component = (this.context as AlarmsScreen.DependencyResolver).getComponent()
        println("BroadcastReceiver")
        //  Toast.makeText(context, "result ", Toast.LENGTH_SHORT).show()
        //  activityStart(context)
       // notification(context)
         CoroutineScope(Dispatchers.Main).launch {

             val result = getCurrentPlaylistTracksUseCase()
             println("result getCurrentPlaylistTracksUseCase")
             Toast.makeText(context, "result ", Toast.LENGTH_SHORT).show()
             when (result) {
                 is DomainResult.Success -> {
                     println("result SUCCESS")
                     setTracksToPlayerPlaylistUseCase(
                         SetTracksToPlayerPlaylistUseCase.Params(
                             result.data
                         )
                     )
                 }
                 is DomainResult.Error -> TODO()
             }
         }
        println("BroadcastReceiver exit")
    }

    override fun onMediaControllerReady(controller: MediaController) {

    }

    private fun notification(context: Context) {
        val intent = Intent(context, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        /* .apply {
         flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK /*or
             FLAG_INCLUDE_STOPPED_PACKAGES*/
     }*/
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(context, "yes")
            .setSmallIcon(com.yes.coreui.R.drawable.app_icon)
            /*  .setContentTitle("My notification")
              .setContentText("Hello World from broadcast receiver!")*/
            //  .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setFullScreenIntent(pendingIntent, true)
            //.setOngoing(true)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1234, builder.build())
    }

    private fun activityStart(context: Context) {
        val intent = Intent(context, AlarmActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
    }
}