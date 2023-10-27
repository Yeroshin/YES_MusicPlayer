package com.yes.musicplayer

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.yes.core.di.module.DataModule
import com.yes.musicplayer.di.components.DaggerMainActivityComponent

import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.musicplayer.di.module.MainActivityModule
import com.yes.musicplayer.presentation.MainActivity
import com.yes.player.di.components.DaggerPlayerFeatureComponent
import com.yes.player.di.components.PlayerFeatureComponent

class YESApplication : Application(),
    MainActivity.DependencyResolver {

    private var mainActivityComponent: MainActivityComponent? = null


    override fun getMainActivityComponent(activity: FragmentActivity): MainActivityComponent {
        return mainActivityComponent ?: DaggerMainActivityComponent
            .builder()
            .mainActivityModule(MainActivityModule(activity))
            // .trackDialogModule(TrackDialogModule())
            //  .dataModule(DataModule(activity))
            .build()
    }
}