package com.yes.musicplayer.di.components


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yes.core.di.DataModule
import com.yes.musicplayer.di.module.MainActivityModule
import com.yes.player.di.module.PlayerModule
import com.yes.player.presentation.MusicService
import com.yes.playlistdialogfeature.di.module.PlayListDialogModule
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import com.yes.playlistfeature.di.module.PlayListModule
import com.yes.playlistfeature.presentation.ui.Playlist
import com.yes.trackdialogfeature.di.module.TrackDialogModule
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MainActivityModule::class,
        PlayListModule::class,
        TrackDialogModule::class,
        DataModule::class,
        PlayListDialogModule::class,
        PlayerModule::class
    ]
)
interface MainActivityComponent {

    fun getPlayerFragment(): Fragment
    fun getFragmentAdapter(): FragmentStateAdapter
    fun getFragmentFactory(): FragmentFactory
    fun getTrackDialogFeatureDependency(): TrackDialog.Dependency
    fun getPlayListDialogFeatureDependency(): PlayListDialog.Dependency
    fun getPlaylistFeatureDependency(): Playlist.Dependency
    fun getMusicServiceDependency(): MusicService.Dependency
}