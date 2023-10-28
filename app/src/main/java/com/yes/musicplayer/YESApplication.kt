package com.yes.musicplayer

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.yes.core.di.module.DataModule
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
import com.yes.playlistfeature.presentation.ui.Playlist
import com.yes.trackdialogfeature.di.component.DaggerTrackDialogComponent
import com.yes.trackdialogfeature.di.component.TrackDialogComponent
import com.yes.trackdialogfeature.presentation.ui.TrackDialog

class YESApplication : Application(),
    MainActivity.DependencyResolver,
    PlayerFragment.DependencyResolver,
    PlayListDialog.DependencyResolver,
    Playlist.DependencyResolver,
    TrackDialog.DependencyResolver {

    private val dataModule = DataModule(this)
    override fun getMainActivityComponent(activity: FragmentActivity): MainActivityComponent {
        return  DaggerMainActivityComponent
            .builder()
            .mainActivityModule(MainActivityModule(activity))
            .build()
    }

    override fun getPlayerFragmentComponent(): PlayerFeatureComponent {
        return DaggerPlayerFeatureComponent.builder()
            .dataModule(dataModule)
            .build()
    }

    override fun getPlayListDialogComponent(): PlayListDialogComponent {
        return DaggerPlayListDialogComponent.builder()
            .dataModule(dataModule)
            .build()
    }

    override fun getPlaylistComponent(): PlaylistComponent {
        return DaggerPlaylistComponent.builder()
            .dataModule(dataModule)
            .build()
    }

    override fun getTrackDialogComponent(): TrackDialogComponent {
        return DaggerTrackDialogComponent.builder()
            .dataModule(dataModule)
            .build()
    }
}