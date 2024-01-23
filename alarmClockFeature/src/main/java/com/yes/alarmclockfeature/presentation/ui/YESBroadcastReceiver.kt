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


class YESBroadcastReceiver : BroadcastReceiver(){
    private lateinit var component: BaseComponent

    private val getCurrentPlaylistTracksUseCase by lazy {
        (component as AlarmClockComponent).getGetCurrentPlaylistTracksUseCase()
    }
    private val setTracksToPlayerPlaylistUseCase by lazy {
        (component as AlarmClockComponent).getSetTracksToPlayerPlaylistUseCase()
    }

    private lateinit var context: Context
    override fun onReceive(context: Context, intent: Intent) {
        this.context = context.applicationContext
        component = (this.context as AlarmsScreen.DependencyResolver).getComponent()
         CoroutineScope(Dispatchers.Main).launch {
             val result = getCurrentPlaylistTracksUseCase()
             when (result) {
                 is DomainResult.Success -> {
                     setTracksToPlayerPlaylistUseCase(
                         SetTracksToPlayerPlaylistUseCase.Params(
                             result.data
                         )
                     )
                 }
                 is DomainResult.Error -> TODO()
             }
         }
    }
}