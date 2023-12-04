package com.yes.musicplayer

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.yes.core.di.component.DaggerAudioSessionIdComponent
import com.yes.core.di.component.DaggerCoreComponent
import com.yes.core.di.component.DaggerMusicServiceComponent
import com.yes.core.di.component.MusicServiceComponent
import com.yes.core.di.module.AudioSessionIdModule
import com.yes.core.di.module.DataModule
import com.yes.core.di.module.MusicServiceModule
import com.yes.core.presentation.MusicService
import com.yes.musicplayer.di.components.DaggerMainActivityComponent

import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.musicplayer.di.module.MainActivityModule
import com.yes.musicplayer.presentation.MainActivity
import com.yes.player.di.components.DaggerPlayerFeatureComponent
import com.yes.player.di.components.PlayerFeatureComponent
import com.yes.player.presentation.ui.PlayerFragment
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
    PlayerFragment.DependencyResolver,
    PlayListDialog.DependencyResolver,
    PlaylistScreen.DependencyResolver,
    TrackDialog.DependencyResolver,
    MusicService.DependencyResolver
     {

    private val dataModule by lazy {
        DataModule(this)
    }
    private val coreComponent by lazy {
        DaggerCoreComponent.builder()
            .dataModule(dataModule)
            .build()
    }

    override fun getMainActivityComponent(activity: FragmentActivity): MainActivityComponent {
        return DaggerMainActivityComponent.builder()
            .mainActivityModule(MainActivityModule(activity))
            .build()
    }

    override fun getPlayerFragmentComponent(): PlayerFeatureComponent {
        return DaggerPlayerFeatureComponent.builder()
            .audioSessionIdComponent(audioSessionIdComponent)
            .coreComponent(coreComponent)
            .build()
    }

    override fun getPlayListDialogComponent(): PlayListDialogComponent {
        return DaggerPlayListDialogComponent.builder()
            .coreComponent(coreComponent)
            .build()
    }

    override fun getPlaylistComponent(): PlaylistComponent {
        return DaggerPlaylistComponent.builder()
            .coreComponent(coreComponent)
            .build()
    }

    override fun getTrackDialogComponent(): TrackDialogComponent {
        return DaggerTrackDialogComponent.builder()
            .coreComponent(coreComponent)
            .build()
    }

    private val musicServiceModule by lazy {
        MusicServiceModule()
    }
    private val audioSessionIdComponent by lazy {
        DaggerAudioSessionIdComponent.builder()
            .audioSessionIdModule(AudioSessionIdModule())
            .dataModule(dataModule)
            .build()
    }

    override fun getMusicServiceComponent(context: Context): MusicServiceComponent {
        return DaggerMusicServiceComponent.builder()
            .coreComponent(coreComponent)
            .musicServiceModule(musicServiceModule)
            .audioSessionIdComponent(audioSessionIdComponent)
            .build()
    }
}