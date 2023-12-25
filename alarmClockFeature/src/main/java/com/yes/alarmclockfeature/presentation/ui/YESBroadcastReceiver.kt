package com.yes.alarmclockfeature.presentation.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent



import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import com.yes.alarmclockfeature.di.components.AlarmClockComponent
import com.yes.core.data.dataSource.PlayerDataSource
import com.yes.core.di.component.BaseComponent
import com.yes.core.di.component.CoreComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class YESBroadcastReceiver : BroadcastReceiver(), PlayerDataSource.MediaControllerListener {
    lateinit var component: BaseComponent
    val player by lazy {
        (component as AlarmClockComponent).getPlayerDataSource()
    }
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("alarm","YESBroadcastReceiver!")
        component=(context.applicationContext as AlarmsScreen.DependencyResolver).getComponent()

        player.setMediaControllerListener(this)

        Log.d("alarm","fired!")
    }

    override fun onMediaControllerReady(controller: MediaController) {
        val mediaMetadata = MediaMetadata.Builder()
            .setAlbumTitle("item.album")
            .setArtist("item.artist")
            .setTitle("item.title")
            .build()
        val items= listOf(
            MediaItem.Builder()
                .setUri("https://www.mediacollege.com/audio/tone/files/10kHz_44100Hz_16bit_05sec.mp3")
                .setMediaMetadata(mediaMetadata)
                .build()
        )
        player.setTracks(items)
        player.play()
    }
}