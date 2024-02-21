package com.yes.musicplayer.equalizer.di.module

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.audiofx.Equalizer
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.core.di.module.IoDispatcher
import com.yes.musicplayer.equalizer.data.repository.EqualizerRepositoryImpl
import com.yes.musicplayer.equalizer.data.repository.SettingsRepositoryImpl
import com.yes.musicplayer.equalizer.domain.usecase.GetEqualizerUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetEqualizerValueUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetPresetUseCase
import com.yes.musicplayer.equalizer.presentation.mapper.MapperUI
import com.yes.musicplayer.equalizer.presentation.ui.EqualizerScreen
import com.yes.musicplayer.equalizer.presentation.vm.EqualizerViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class EqualizerModule {
    @Provides
    fun providesSetEqualizerValueUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        settingsRepository: SettingsRepositoryImpl,
        equalizerRepository: EqualizerRepositoryImpl
    ): SetEqualizerValueUseCase {
        return SetEqualizerValueUseCase(
            dispatcher,
            settingsRepository,
            equalizerRepository
        )
    }
    @Provides
    fun providesSetPresetValuesUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        settingsRepository: SettingsRepositoryImpl,
        equalizerRepository: EqualizerRepositoryImpl
    ): SetPresetUseCase {
        return SetPresetUseCase(
            dispatcher,
            settingsRepository,
            equalizerRepository
        )
    }

   /* @Provides
    fun providesEqualizer(
        audioSessionId:Int
    ): Equalizer{
        return Equalizer(0,audioSessionId)
    }*/
    @OptIn(UnstableApi::class)
    @Provides
    fun providesEqualizer(
       context: Context,
       player: ExoPlayer
    ): Equalizer{
        val t=player.audioSessionId
       val playe=  ExoPlayer.Builder(context).build()
         return Equalizer(0,playe.audioSessionId)
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
        mapperUI: MapperUI,
        getEqualizerUseCase:GetEqualizerUseCase,
        setPresetUseCase:SetPresetUseCase,
        setEqualizerValueUseCase: SetEqualizerValueUseCase
    ): EqualizerViewModel.Factory {
        return EqualizerViewModel.Factory(
            mapperUI,
            getEqualizerUseCase,
            setPresetUseCase,
            setEqualizerValueUseCase
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