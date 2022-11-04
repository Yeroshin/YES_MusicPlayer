package com.yes.musicplayer.di.components

import android.app.Activity
import com.yes.musicplayer.MainActivity
import com.yes.musicplayer.di.module.MainActivityModule
import dagger.Component
import javax.inject.Singleton


@Component(modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}