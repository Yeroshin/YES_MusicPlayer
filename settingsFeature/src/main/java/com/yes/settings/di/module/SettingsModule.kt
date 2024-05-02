package com.yes.settings.di.module

import com.yes.core.data.dataSource.SettingsDataSource
import com.yes.core.data.repository.SettingsRepositoryImpl
import com.yes.core.di.module.IoDispatcher
import com.yes.core.presentation.ui.BaseDependency
import com.yes.settings.domain.usecase.GetThemeUseCase
import com.yes.settings.domain.usecase.SetThemeUseCase
import com.yes.settings.presentation.vm.SettingsViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class SettingsModule {
    @Provides
    fun providesSettingsRepositoryImpl(
        settingsDataSource: SettingsDataSource
    ): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(
            settingsDataSource
        )
    }
    @Provides
    fun providesSetThemeUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SetThemeUseCase {
        return SetThemeUseCase(
            dispatcher,
            settingsRepositoryImpl
        )
    }
    @Provides
    fun providesGetThemeUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): GetThemeUseCase {
        return GetThemeUseCase(
            dispatcher,
            settingsRepositoryImpl
        )
    }
    @Provides
    fun providesSettingsViewModelFactory(
        setThemeUseCase: SetThemeUseCase,
        getThemeUseCase: GetThemeUseCase
    ): SettingsViewModel.Factory {
        return SettingsViewModel.Factory(
            setThemeUseCase,
            getThemeUseCase
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