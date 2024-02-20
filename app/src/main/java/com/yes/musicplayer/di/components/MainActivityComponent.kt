package com.yes.musicplayer.di.components

import com.yes.musicplayer.di.MainActivityScope
import com.yes.musicplayer.di.module.MainActivityModule
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