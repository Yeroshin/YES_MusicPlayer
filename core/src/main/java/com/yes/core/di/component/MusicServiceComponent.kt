package com.yes.core.di.component

import com.yes.core.di.MusicServiceScope
import com.yes.core.di.module.DataModule
import com.yes.core.di.module.MusicServiceModule
import com.yes.core.presentation.MusicService
import dagger.Component
import javax.inject.Singleton

@MusicServiceScope
@Component(
    dependencies = [
        CoreComponent::class,
    AudioSessionIdComponent::class
    ],
    modules = [
        MusicServiceModule::class,
    ]
)

interface MusicServiceComponent {
    fun getDependency(): MusicService.Dependency
}