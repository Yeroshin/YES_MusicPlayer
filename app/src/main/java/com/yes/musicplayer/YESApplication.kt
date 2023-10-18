package com.yes.musicplayer

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.yes.core.di.DataModule
import com.yes.musicplayer.di.components.DaggerMainActivityComponent

import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.musicplayer.di.module.MainActivityModule
import com.yes.musicplayer.presentation.MainActivity
import com.yes.player.presentation.MusicService

class YESApplication: Application(),
    MainActivity.DependencyResolver,
MusicService.DependencyResolver{


    private lateinit var musicServiceDependency: MusicService.Dependency
    private lateinit var mainActivityComponent: MainActivityComponent
    override fun getMainActivityComponent(activity: FragmentActivity): MainActivityComponent {
        mainActivityComponent=DaggerMainActivityComponent
            .builder()
            .mainActivityModule(MainActivityModule(activity))
           // .trackDialogModule(TrackDialogModule())
            .dataModule(DataModule(activity))
            .build()
        return mainActivityComponent
    }
    override fun getMusicServiceComponent(): MusicService.Dependency{
        return mainActivityComponent.getMusicServiceDependency()
    }




}