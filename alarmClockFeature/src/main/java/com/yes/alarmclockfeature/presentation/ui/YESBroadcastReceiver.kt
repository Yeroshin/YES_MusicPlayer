package com.yes.alarmclockfeature.presentation.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yes.alarmclockfeature.domain.usecase.SetAndPlayTracksToPlayerPlaylistUseCase
import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class YESBroadcastReceiver : BroadcastReceiver(){
   // private lateinit var component: BaseComponent
    private lateinit var context: Context
    private val component by lazy {
         (this.context as AlarmsScreen.DependencyResolver).getAlarmsScreenComponent()

    }
    private val getCurrentPlaylistTracksUseCase by lazy {
        component.getGetCurrentPlaylistTracksUseCase()
    }
    private val setAndPlayTracksToPlayerPlaylistUseCase by lazy {
        component.getSetAndPlayTracksToPlayerPlaylistUseCase()
    }
    private val setNearestAlarmUseCase by lazy {
        component.getSetNearestAlarmUseCase()
    }


    override fun onReceive(context: Context, intent: Intent) {
        this.context = context.applicationContext
       // component = (this.context as AlarmsScreen.DependencyResolver).getAlarmsScreenComponent()
         CoroutineScope(Dispatchers.Main).launch {
             when (val result = getCurrentPlaylistTracksUseCase()) {
                 is DomainResult.Success -> {
                     setAndPlayTracksToPlayerPlaylistUseCase(
                         SetAndPlayTracksToPlayerPlaylistUseCase.Params(
                             result.data
                         )
                     )
                 }
                 is DomainResult.Error -> {}
             }
             when (val result = setNearestAlarmUseCase()) {
                 is DomainResult.Success -> {}
                 is DomainResult.Error -> {}
             }
         }
    }
}