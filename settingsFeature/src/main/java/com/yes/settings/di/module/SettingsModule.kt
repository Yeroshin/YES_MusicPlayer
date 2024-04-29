package com.yes.settings.di.module

import com.yes.core.presentation.BaseDependency
import com.yes.settings.presentation.vm.SettingsViewModel
import dagger.Module
import dagger.Provides

@Module
class SettingsModule {
    @Provides
    fun providesPlayerViewModelFactory(

        ): SettingsViewModel.Factory {
        return SettingsViewModel.Factory(


            )
    }
    @Provides
    fun providesDependency(
        factory: SettingsViewModel.Factory,
    ): BaseDependency {
        return BaseDependency(
            factory
        )
    }
}