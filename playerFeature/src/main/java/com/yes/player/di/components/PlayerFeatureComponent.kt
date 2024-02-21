package com.yes.player.di.components

import com.yes.core.di.component.AudioComponent
import com.yes.core.di.component.DataComponent
import com.yes.player.di.PlayerScope
import com.yes.player.di.module.PlayerModule
import com.yes.player.di.module.UseCaseModule
import com.yes.player.presentation.ui.PlayerScreen
import dagger.Component

@Component(
    dependencies = [
        DataComponent::class,
        AudioComponent::class
    ],
    modules = [
        PlayerModule::class,
        UseCaseModule::class,

    ]
)
@PlayerScope
interface PlayerFeatureComponent {
    fun getDependency(): PlayerScreen.Dependency
}