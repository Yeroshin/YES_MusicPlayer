package com.yes.musicplayer.equalizer.di.module

import android.content.Context
import android.media.audiofx.Equalizer
import android.media.audiofx.LoudnessEnhancer
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.core.di.module.IoDispatcher
import com.yes.musicplayer.equalizer.data.mapper.Mapper
import com.yes.musicplayer.equalizer.data.repository.EqualizerRepositoryImpl
import com.yes.musicplayer.equalizer.data.repository.LoudnessEnhancerRepository
import com.yes.musicplayer.equalizer.data.repository.SettingsRepositoryImpl
import com.yes.musicplayer.equalizer.domain.usecase.GetAudioEffectUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetEqualizerEnabledUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetEqualizerValueUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetLoudnessEnhancerEnabledUseCase
import com.yes.musicplayer.equalizer.domain.usecase.SetLoudnessEnhancerValueUseCase
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
    fun providesSetEqualizerEnabledUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        settingsRepository: SettingsRepositoryImpl,
        equalizerRepository: EqualizerRepositoryImpl
    ): SetEqualizerEnabledUseCase {
        return SetEqualizerEnabledUseCase(
            dispatcher,
            settingsRepository,
            equalizerRepository
        )
    }

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
    ): Equalizer {
        /*  var eq=Equalizer(1000,player.audioSessionId)
         /* eq.setControlStatusListener { effect, controlGranted ->
              if(!controlGranted){
                // eq.release()
                //  eq=Equalizer(1000,player.audioSessionId)
                  val a=eq.hasControl()
                  val b=eq.enabled
                  eq.enabled=true
                  val c=eq.hasControl()
                  val d=eq.enabled
                  val hasControl = controlGranted
              }

          }*/
          try{
              eq.usePreset(1)
          }catch (exception: Exception){
              // equalizer.release()
              val pr=exception
          }
          val a=eq.hasControl()
          val b=eq.enabled
          eq.enabled=true
          val c=eq.hasControl()
          val d=eq.enabled

          try{
              eq.usePreset(1)
          }catch (exception: Exception){
              // equalizer.release()
              val pr=exception
          }
          val z=eq.hasControl()
          return eq*/
      /*  val eq = Equalizer(0, player.audioSessionId)
        eq.enabled = true//tmp
        return eq*/
         return Equalizer(0,player.audioSessionId)

    }

    @Provides
    fun providesSettingsRepositoryImpl(
        settings: SettingsDataStore
    ): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(
            settings
        )
    }

    @OptIn(UnstableApi::class)
    @Provides
    fun providesEqualizerRepositoryImpl(
        equalizer: Equalizer,
        player: ExoPlayer
    ): EqualizerRepositoryImpl {
        return EqualizerRepositoryImpl(
            equalizer,
            player.audioSessionId
        )
    }

    @Provides
    fun providesMapperUI(): MapperUI {
        return MapperUI()
    }
    @Provides
    fun providesMapper(): Mapper {
        return Mapper()
    }

    @Provides
    fun providesGetEqualizerUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        settingsRepository: SettingsRepositoryImpl,
        equalizerRepository: EqualizerRepositoryImpl,
        loudnessEnhancerRepository: LoudnessEnhancerRepository
    ): GetAudioEffectUseCase {
        return GetAudioEffectUseCase(
            dispatcher,
            settingsRepository,
            equalizerRepository,
            loudnessEnhancerRepository
        )
    }
    @OptIn(UnstableApi::class) @Provides
    fun providesLoudnessEnhancer(
        player: ExoPlayer
    ): LoudnessEnhancer {
        return LoudnessEnhancer(
            player.audioSessionId
        )
    }
    @Provides
    fun providesLoudnessEnhancerRepository(
        loudnessEnhancer: LoudnessEnhancer,
        mapper: Mapper,
        audioSessionId:Int
    ): LoudnessEnhancerRepository {
        return LoudnessEnhancerRepository(
            loudnessEnhancer,
            mapper,
            audioSessionId
        )
    }
    @Provides
    fun providesSetLoudnessEnhancerEnabledUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        settingsRepository: SettingsRepositoryImpl,
        loudnessEnhancerRepository: LoudnessEnhancerRepository
    ): SetLoudnessEnhancerEnabledUseCase {
        return SetLoudnessEnhancerEnabledUseCase(
            dispatcher,
            settingsRepository,
            loudnessEnhancerRepository
        )
    }
    @Provides
    fun providesSetLoudnessEnhancerValueUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        settingsRepository: SettingsRepositoryImpl,
        loudnessEnhancerRepository: LoudnessEnhancerRepository
    ): SetLoudnessEnhancerValueUseCase {
        return SetLoudnessEnhancerValueUseCase(
            dispatcher,
            settingsRepository,
            loudnessEnhancerRepository
        )
    }

    @Provides
    fun providesEqualizerViewModelFactory(
        mapperUI: MapperUI,
        getAudioEffectUseCase: GetAudioEffectUseCase,
        setPresetUseCase: SetPresetUseCase,
        setEqualizerValueUseCase: SetEqualizerValueUseCase,
        setEqualizerEnabledUseCase: SetEqualizerEnabledUseCase,
        setLoudnessEnhancerEnabledUseCase: SetLoudnessEnhancerEnabledUseCase,
        setLoudnessEnhancerValueUseCase: SetLoudnessEnhancerValueUseCase

    ): EqualizerViewModel.Factory {
        return EqualizerViewModel.Factory(
            mapperUI,
            getAudioEffectUseCase,
            setPresetUseCase,
            setEqualizerValueUseCase,
            setEqualizerEnabledUseCase,
            setLoudnessEnhancerEnabledUseCase,
            setLoudnessEnhancerValueUseCase

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