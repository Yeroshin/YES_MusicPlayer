package com.yes.core.di.component

import com.yes.core.di.module.AudioSessionIdModule
import com.yes.core.di.module.DataModule
import com.yes.core.di.module.MusicServiceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(

    modules = [
        AudioSessionIdModule::class,
    DataModule::class
    ]
)
interface AudioSessionIdComponent {
    fun getAudioSessionId():Int
}