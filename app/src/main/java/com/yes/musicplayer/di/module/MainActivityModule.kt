package com.yes.musicplayer.di.module

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.yes.musicplayer.presentation.FragmentAdapter
import com.yes.player.presentation.PlayerFragment
import com.yes.playlistfeature.presentation.PlaylistFragment

import dagger.Module
import dagger.Provides

@Module
internal class MainActivityModule(private val activity: FragmentActivity) {

    @Provides
    fun provideFragment(): Fragment {
        return PlayerFragment()
    }

    @Provides
    fun provideFragmentAdapter(): FragmentAdapter {

        val fragmentsList = mutableListOf<Fragment>()
        val playerFragment = PlaylistFragment()
        fragmentsList.add(playerFragment)
        return FragmentAdapter(activity, fragmentsList)
    }
}