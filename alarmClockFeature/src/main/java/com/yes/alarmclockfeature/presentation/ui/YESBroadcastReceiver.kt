package com.yes.alarmclockfeature.presentation.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent



import android.util.Log
import android.widget.Toast
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
    private lateinit var component: BaseComponent
    private val player by lazy {
        (component as AlarmClockComponent).getPlayerDataSource()
    }
    private lateinit var context: Context
    override fun onReceive(context: Context, intent: Intent) {
        this.context=context
        Log.d("alarm","YESBroadcastReceiver!")
        component=(context.applicationContext as AlarmsScreen.DependencyResolver).getComponent()

        player.setMediaControllerListener(this)


        Toast.makeText(context, "Don't panic but your time is up!!!!.",
            Toast.LENGTH_LONG).show();
        Log.d("alarm","fired!")
       println("YESBroadcastReceiver!")
    }

    override fun onMediaControllerReady(controller: MediaController) {
        Toast.makeText(context, "STARTING service!!!!.",
            Toast.LENGTH_LONG).show();
        val mediaMetadata = MediaMetadata.Builder()
            .setAlbumTitle("item.album")
            .setArtist("item.artist")
            .setTitle("item.title")
            .build()
        val items= listOf(
            MediaItem.Builder()
                .setUri("https://maximum.hostingradio.ru/maximum96.aacp")
                .setMediaMetadata(mediaMetadata)
                .build()
        )
        player.setTracks(items)
        player.play()
    }
}