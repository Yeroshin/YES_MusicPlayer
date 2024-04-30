package com.yes.musicplayer.di.components

import com.yes.core.di.component.DataComponent
import com.yes.core.di.module.DataModule
import com.yes.core.presentation.ui.BaseDependency
import com.yes.musicplayer.di.MainActivityScope
import com.yes.musicplayer.di.module.MainActivityModule
import dagger.Component


@Component(
    dependencies = [
        DataComponent::class,
    ],
   modules = [
        MainActivityModule::class,
    ]
)
@MainActivityScope
interface MainActivityComponent {
    fun getDependency(): BaseDependency
}