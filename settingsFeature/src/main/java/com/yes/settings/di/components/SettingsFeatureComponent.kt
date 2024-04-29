package com.yes.settings.di.components

import com.yes.core.di.component.AudioComponent
import com.yes.core.di.component.DataComponent
import com.yes.core.presentation.BaseDependency
import com.yes.settings.di.SettingsScope
import com.yes.settings.di.module.SettingsModule
import dagger.Component

@Component(
    dependencies = [
      //  DataComponent::class,
    ],
    modules = [
        SettingsModule::class

    ]
)
@SettingsScope
interface SettingsFeatureComponent {
    fun getDependency(): BaseDependency
}