package com.yes.alarmclockfeature.presentation.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent



import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.yes.core.di.component.CoreComponent

class YESBroadcastReceiver  : BroadcastReceiver() {
    interface YESBroadcastDependency{
        fun getMyCoreComponent(): CoreComponent
    }
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("alarm","fired!")
        val component =(context.applicationContext as YESBroadcastDependency).getMyCoreComponent()
        val player=component.providesPlayerDataSource()
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
        Log.d("alarm","fired!")
    }
}