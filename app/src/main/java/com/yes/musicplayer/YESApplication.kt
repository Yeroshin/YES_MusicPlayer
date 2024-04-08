package com.yes.musicplayer

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.yes.alarmclockfeature.di.components.AlarmClockComponent
import com.yes.alarmclockfeature.di.components.DaggerAlarmClockComponent
import com.yes.alarmclockfeature.di.module.AlarmClockModule
import com.yes.alarmclockfeature.presentation.ui.AlarmsScreen
import com.yes.core.di.component.DaggerAudioComponent
import com.yes.core.di.component.DaggerDataComponent
import com.yes.core.di.component.DaggerMusicServiceComponent
import com.yes.core.di.component.MusicServiceComponent
import com.yes.core.di.module.AudioModule
import com.yes.core.di.module.DataModule
import com.yes.core.di.module.MusicServiceModule
import com.yes.core.presentation.MusicService
import com.yes.musicplayer.di.components.DaggerMainActivityComponent
import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.musicplayer.equalizer.di.components.DaggerEqualizerComponent
import com.yes.musicplayer.equalizer.di.components.EqualizerComponent
import com.yes.musicplayer.equalizer.di.module.EqualizerModule
import com.yes.musicplayer.equalizer.presentation.ui.EqualizerScreen
import com.yes.musicplayer.presentation.MainActivity
import com.yes.player.di.components.DaggerPlayerFeatureComponent
import com.yes.player.di.components.PlayerFeatureComponent
import com.yes.player.presentation.ui.PlayerScreen
import com.yes.playlistdialogfeature.di.component.DaggerPlayListDialogComponent
import com.yes.playlistdialogfeature.di.component.PlayListDialogComponent
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import com.yes.playlistfeature.di.component.DaggerPlaylistComponent
import com.yes.playlistfeature.di.component.PlaylistComponent
import com.yes.playlistfeature.presentation.ui.PlaylistScreen
import com.yes.trackdialogfeature.di.component.DaggerTrackDialogComponent
import com.yes.trackdialogfeature.di.component.TrackDialogComponent
import com.yes.trackdialogfeature.presentation.ui.TrackDialog

class YESApplication : Application(),
    MainActivity.DependencyResolver,
    PlayerScreen.DependencyResolver,
    PlayListDialog.DependencyResolver,
    PlaylistScreen.DependencyResolver,
    TrackDialog.DependencyResolver,
    MusicService.DependencyResolver,
    AlarmsScreen.DependencyResolver,
    EqualizerScreen.DependencyResolver
{

    private val dataModule by lazy {
        DataModule(this)
    }
     private val dataComponent by lazy {
        DaggerDataComponent.builder()
            .dataModule(dataModule)
            .build()
    }

    override fun getMainActivityComponent(activity: FragmentActivity): MainActivityComponent {
        return DaggerMainActivityComponent.builder()
            .build()
    }

   /* override fun getPlayerFragmentComponent(): PlayerFeatureComponent {
        return DaggerPlayerFeatureComponent.builder()
            .audioComponent(audioComponent)
            .dataComponent(dataComponent)
            .build()
    }*/
    private val dep by lazy {
       DaggerPlayerFeatureComponent.builder()
           .audioComponent(audioComponent)
           .dataComponent(dataComponent)
           .build().getDependency()
   }
   override fun getPlayerFragmentComponent(): PlayerScreen.Dependency {
       return dep
   }


    override fun getPlayListDialogComponent(): PlayListDialogComponent {
        return DaggerPlayListDialogComponent.builder()
            .dataComponent(dataComponent)
            .build()
    }

    override fun getPlaylistComponent(): PlaylistComponent {
        return DaggerPlaylistComponent.builder()
            .dataComponent(dataComponent)
            .build()
    }

    override fun getTrackDialogComponent(): TrackDialogComponent {
        return DaggerTrackDialogComponent.builder()
            .dataComponent(dataComponent)
            .build()
    }

    private val musicServiceModule by lazy {
        MusicServiceModule()
    }
    private val audioComponent by lazy {
        DaggerAudioComponent.builder()
            .dataComponent(dataComponent)
            .audioModule(AudioModule())
            .build()
    }
/*private val musicServiceComponent by lazy{
    DaggerMusicServiceComponent.builder()
        .coreComponent(coreComponent)
        .musicServiceModule(musicServiceModule)
        .build()
}*/
    override fun getMusicServiceComponent(context: Context): MusicServiceComponent {
        return DaggerMusicServiceComponent.builder()
            .dataComponent(dataComponent)
            .musicServiceModule(musicServiceModule)
            .audioComponent(audioComponent)
           .build()
      //  return musicServiceComponent
    }

    override fun getAlarmsScreenComponent(): AlarmClockComponent {
        return DaggerAlarmClockComponent.builder()
            .alarmClockModule(AlarmClockModule(MainActivity::class.java))
            .dataComponent(dataComponent)
            .build()
    }

    override fun getEqualizerScreenComponent(): EqualizerComponent {
        return DaggerEqualizerComponent.builder()
            .dataComponent(dataComponent)
            .audioComponent(audioComponent)
            .equalizerModule(EqualizerModule())
            .build()
    }
}