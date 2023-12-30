package com.yes.alarmclockfeature.presentation.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent



import android.util.Log
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import com.yes.alarmclockfeature.di.components.AlarmClockComponent
import com.yes.alarmclockfeature.domain.usecase.SetTracksToPlayerPlaylistUseCase
import com.yes.core.data.dataSource.PlayerDataSource
import com.yes.core.di.component.BaseComponent
import com.yes.core.di.component.CoreComponent
import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class YESBroadcastReceiver : BroadcastReceiver(),PlayerDataSource.MediaControllerListener {
    private lateinit var component: BaseComponent

    private val getCurrentPlaylistTracksUseCase by lazy {
        (component as AlarmClockComponent).getGetCurrentPlaylistTracksUseCase()
    }
    private val setTracksToPlayerPlaylistUseCase by lazy {
        (component as AlarmClockComponent).getSetTracksToPlayerPlaylistUseCase()
    }
    private lateinit var context: Context
    override fun onReceive(context: Context, intent: Intent) {
        this.context=context
        component=(context.applicationContext as AlarmsScreen.DependencyResolver).getComponent()
        Toast.makeText(context, "player ", Toast.LENGTH_SHORT).show()

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
    override fun onMediaControllerReady(controller: MediaController) {

    }
}