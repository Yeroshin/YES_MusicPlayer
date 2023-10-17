package com.yes.musicplayer

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.yes.core.di.DataModule
import com.yes.musicplayer.di.components.DaggerMainActivityComponent

import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.musicplayer.di.module.MainActivityModule
import com.yes.musicplayer.presentation.MainActivity

class YESApplication: Application(),MainActivity.DependencyResolver {




    override fun getMainActivityComponent(activity: FragmentActivity): MainActivityComponent {
        return DaggerMainActivityComponent
            .builder()
            .mainActivityModule(MainActivityModule(activity))
           // .trackDialogModule(TrackDialogModule())
            .dataModule(DataModule(activity))
            .build()

    }




}