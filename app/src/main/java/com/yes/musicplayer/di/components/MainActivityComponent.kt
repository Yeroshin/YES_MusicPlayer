package com.yes.musicplayer.di.components


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yes.core.di.DataModule
import com.yes.musicplayer.presentation.MainActivity
import com.yes.musicplayer.di.module.MainActivityModule
import com.yes.musicplayer.presentation.UniversalFragmentAdapter
import com.yes.playlistdialogfeature.di.module.PlayListDialogModule
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import com.yes.playlistfeature.di.module.PlayListModule
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
        PlayListDialogModule::class
    ]
)
interface MainActivityComponent {

    fun getPlayerFragment(): Fragment
    fun getFragmentAdapter(): FragmentStateAdapter
    fun getFragmentFactory(): FragmentFactory
    fun getTrackDialogDependency(): TrackDialog.Dependency
    fun getPlayListDialogDependency(): PlayListDialog.Dependency
}