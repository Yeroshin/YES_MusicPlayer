package com.yes.core.di.component

import com.yes.core.di.MusicServiceScope
import com.yes.core.di.module.MusicServiceModule
import com.yes.core.presentation.MusicService
import dagger.Component

@MusicServiceScope
@Component(
    dependencies = [
        DataComponent::class,
    AudioComponent::class
    ],
    modules = [
        MusicServiceModule::class,
    ]
)

interface MusicServiceComponent {
    fun getDependency(): MusicService.Dependency
}