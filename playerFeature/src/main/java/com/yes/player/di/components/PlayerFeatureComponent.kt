package com.yes.player.di.components

import com.yes.core.di.component.CoreComponent
import com.yes.core.di.module.DataModule
import com.yes.player.di.PlayerScope
import com.yes.player.di.module.PlayerModule
import com.yes.player.di.module.UseCaseModule
import com.yes.player.presentation.ui.PlayerFragment
import com.yes.player.presentation.vm.PlayerViewModel
import dagger.Component

@Component(
    modules = [
        PlayerModule::class,
        UseCaseModule::class,
        DataModule::class
    ]
)
@PlayerScope
interface PlayerFeatureComponent {
    fun getDependency(): PlayerFragment.Dependency
}