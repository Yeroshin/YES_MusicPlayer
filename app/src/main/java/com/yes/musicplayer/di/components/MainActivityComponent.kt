package com.yes.musicplayer.di.components


import androidx.fragment.app.Fragment
import com.yes.musicplayer.presentation.MainActivity
import com.yes.musicplayer.di.module.MainActivityModule
import com.yes.musicplayer.presentation.FragmentAdapter
import dagger.Component


@Component(modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(activity: MainActivity)
    fun getPlayerFragment(): Fragment
    fun getFragmentAdapter(): FragmentAdapter
}