package com.yes.musicplayer.di.components

import com.yes.musicplayer.presentation.MainActivity
import com.yes.musicplayer.di.module.MainActivityModule
import dagger.Component


@Component(modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}