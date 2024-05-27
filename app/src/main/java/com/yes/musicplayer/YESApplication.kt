package com.yes.musicplayer

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.multidex.MultiDexApplication
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
import com.yes.core.presentation.ui.ActivityDependency
import com.yes.core.presentation.ui.BaseDependency
import com.yes.core.presentation.ui.MusicService
import com.yes.musicplayer.di.components.DaggerMainActivityComponent
import com.yes.musicplayer.equalizer.di.components.DaggerEqualizerComponent
import com.yes.musicplayer.equalizer.di.module.EqualizerModule
import com.yes.musicplayer.equalizer.presentation.ui.EqualizerScreen
import com.yes.musicplayer.presentation.ui.MainActivity
import com.yes.player.di.components.DaggerPlayerFeatureComponent
import com.yes.player.presentation.ui.PlayerScreen
import com.yes.playlistdialogfeature.di.component.DaggerPlayListDialogComponent
import com.yes.playlistdialogfeature.di.component.PlayListDialogComponent
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import com.yes.playlistfeature.di.component.DaggerPlaylistComponent
import com.yes.playlistfeature.presentation.ui.PlaylistScreen
import com.yes.settings.di.components.DaggerSettingsComponent
import com.yes.settings.presentation.ui.SettingsScreen
import com.yes.trackdialogfeature.di.component.DaggerTrackDialogComponent
import com.yes.trackdialogfeature.di.component.TrackDialogComponent
import com.yes.trackdialogfeature.presentation.ui.TrackDialog

class YESApplication : MultiDexApplication(),
    MainActivity.DependencyResolver,
    PlayerScreen.DependencyResolver,
    PlayListDialog.DependencyResolver,
    PlaylistScreen.DependencyResolver,
    TrackDialog.DependencyResolver,
    MusicService.DependencyResolver,
    AlarmsScreen.DependencyResolver,
    EqualizerScreen.DependencyResolver,
    SettingsScreen.DependencyResolver {

    private val dataModule by lazy {
        DataModule(this)
    }
    private val dataComponent by lazy {
        DaggerDataComponent.builder()
            .dataModule(dataModule)
            .build()
    }

    override fun resolveMainActivityDependency(): ActivityDependency {
        return DaggerMainActivityComponent.builder()
            .dataComponent(dataComponent)
            .build().getDependency()
    }

    /* override fun getPlayerFragmentComponent(): PlayerFeatureComponent {
         return DaggerPlayerFeatureComponent.builder()
             .audioComponent(audioComponent)
             .dataComponent(dataComponent)
             .build()
     }*/
    private val playerFragmentDependency by lazy {
        DaggerPlayerFeatureComponent.builder()
            .audioComponent(audioComponent)
            .dataComponent(dataComponent)
            .build().getDependency()
    }

    override fun resolvePlayerFragmentDependency(): BaseDependency {
        return playerFragmentDependency
    }


    override fun getPlayListDialogComponent(): PlayListDialogComponent {
        return DaggerPlayListDialogComponent.builder()
            .dataComponent(dataComponent)
            .build()
    }

    private val playListDependency by lazy {
        DaggerPlaylistComponent.builder()
            .dataComponent(dataComponent)
            .build().getDependency()
    }

    override fun resolvePlaylistComponent(): BaseDependency {
        return playListDependency
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

    private val equalizerPlayListDependency by lazy {
        DaggerEqualizerComponent.builder()
            .dataComponent(dataComponent)
            .audioComponent(audioComponent)
            .equalizerModule(EqualizerModule())
            .build().getDependency()
    }

    override fun resolveEqualizerScreenDependency(): BaseDependency {
        return equalizerPlayListDependency
    }

    override fun resolveSettingsDependency(): BaseDependency {
       return DaggerSettingsComponent.builder()
           .dataComponent(dataComponent)
           .build().getDependency()
    }
}