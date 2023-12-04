package com.yes.musicplayer.di.components

import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yes.musicplayer.di.MainActivityScope
import com.yes.musicplayer.di.module.MainActivityModule
import com.yes.player.di.components.PlayerFeatureComponent
import com.yes.player.presentation.ui.PlayerFragment
import dagger.Component


@Component(
   modules = [
        MainActivityModule::class,
      /*  PlayListModule::class,
        TrackDialogModule::class,
        DataModule::class,
        PlayListDialogModule::class,*/

    ]
)
@MainActivityScope
interface MainActivityComponent {

  //  fun getPlayerFragment(): Fragment
   /* fun getFragmentAdapter(): FragmentStateAdapter
    fun getFragmentFactory(): FragmentFactory*/
   /* fun getTrackDialogFeatureDependency(): TrackDialog.Dependency
    fun getPlayListDialogFeatureDependency(): PlayListDialog.Dependency
    fun getPlaylistFeatureDependency(): Playlist.Dependency*/
  //  fun getPlayerDependency(): PlayerFragment.Dependency
}