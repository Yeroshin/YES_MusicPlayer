package com.yes.musicplayer.equalizer.di.module

import android.media.audiofx.Equalizer
import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.core.di.module.IoDispatcher
import com.yes.musicplayer.equalizer.data.repository.EqualizerRepositoryImpl
import com.yes.musicplayer.equalizer.data.repository.SettingsRepositoryImpl
import com.yes.musicplayer.equalizer.domain.usecase.GetEqualizerUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetPresetValuesUseCase
import com.yes.musicplayer.equalizer.presentation.mapper.MapperUI
import com.yes.musicplayer.equalizer.presentation.ui.EqualizerScreen
import com.yes.musicplayer.equalizer.presentation.vm.EqualizerViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class EqualizerModule {
    @Provides
    fun providesSetPresetValuesUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        equalizerRepository: EqualizerRepositoryImpl
    ): SetPresetValuesUseCase {
        return SetPresetValuesUseCase(
            dispatcher,
            equalizerRepository
        )
    }

    @Provides
    fun providesEqualizer(
        audioSessionId:Int
    ): Equalizer{
        return Equalizer(0,audioSessionId)
    }
    @Provides
    fun providesSettingsRepositoryImpl(
        settings: SettingsDataStore
    ): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(
            settings
        )
    }
    @Provides
    fun providesEqualizerRepositoryImpl(
        equalizer: Equalizer
    ): EqualizerRepositoryImpl{
        return EqualizerRepositoryImpl(
            equalizer
        )
    }
    @Provides
    fun providesMapperUI(): MapperUI {
        return MapperUI()
    }
    @Provides
    fun providesGetEqualizerUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        settingsRepository: SettingsRepositoryImpl,
        equalizerRepository: EqualizerRepositoryImpl
    ): GetEqualizerUseCase {
        return GetEqualizerUseCase(
            dispatcher,
            settingsRepository,
            equalizerRepository
        )
    }

    @Provides
    fun providesEqualizerViewModelFactory(
        getEqualizerUseCase:GetEqualizerUseCase,
        mapperUI: MapperUI,
        setPresetValuesUseCase:SetPresetValuesUseCase
    ): EqualizerViewModel.Factory {
        return EqualizerViewModel.Factory(
            getEqualizerUseCase,
            mapperUI,
            setPresetValuesUseCase
        )
    }

    @Provides
    fun providesDependency(
        factory: EqualizerViewModel.Factory,
    ): EqualizerScreen.Dependency {
        return EqualizerScreen.Dependency(
            factory
        )
    }
}