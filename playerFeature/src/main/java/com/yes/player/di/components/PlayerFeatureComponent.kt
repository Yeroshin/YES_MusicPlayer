package com.yes.player.di.components

import com.yes.core.di.component.AudioSessionIdComponent
import com.yes.core.di.component.CoreComponent
import com.yes.core.di.module.MusicServiceModule
import com.yes.player.di.PlayerScope
import com.yes.player.di.module.PlayerModule
import com.yes.player.di.module.UseCaseModule
import com.yes.player.presentation.ui.PlayerFragment
import dagger.Component

@Component(
    dependencies = [
        CoreComponent::class,
        AudioSessionIdComponent::class
    ],
    modules = [
        PlayerModule::class,
        UseCaseModule::class,

    ]
)
@PlayerScope
interface PlayerFeatureComponent {
    fun getDependency(): PlayerFragment.Dependency
}