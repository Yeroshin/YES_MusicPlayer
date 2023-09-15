package com.yes.musicplayer

import android.app.Application
import androidx.fragment.app.FragmentActivity
import com.yes.core.di.DataModule
import com.yes.musicplayer.di.components.DaggerMainActivityComponent
import com.yes.musicplayer.di.components.DaggerTrackDialogComponent
import com.yes.musicplayer.di.components.MainActivityComponent
import com.yes.musicplayer.di.module.MainActivityModule
import com.yes.musicplayer.presentation.MainActivity
import com.yes.trackdialogfeature.di.module.TrackDialogModule
import com.yes.trackdialogfeature.presentation.ui.TrackDialog

class YESApplication: Application(),MainActivity.Main {
    override fun onCreate() {
        super.onCreate()


    }

    override fun getTrackDialogDependency(): TrackDialog.Dependency {
        return DaggerTrackDialogComponent
            .builder()
            .dataModule(DataModule(this))
            .trackDialogModule(TrackDialogModule())
            .build()
            .getTrackDialogDependency()
    }

    override fun getMainActivityComponent(activity: FragmentActivity): MainActivityComponent {
        return DaggerMainActivityComponent
            .builder()
            .mainActivityModule(MainActivityModule(activity))
            .build()

    }


}