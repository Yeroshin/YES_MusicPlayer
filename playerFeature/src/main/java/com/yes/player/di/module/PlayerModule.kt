package com.yes.player.di.module

import android.media.audiofx.Visualizer
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.data.dataSource.PlayerDataSource
import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.player.data.mapper.Mapper
import com.yes.player.data.repository.PlayerRepository
import com.yes.player.data.repository.PlaylistRepositoryImpl
import com.yes.player.data.repository.SettingsRepositoryImpl
import com.yes.player.data.repository.VisualizerRepository
import com.yes.player.domain.usecase.PlayUseCase
import com.yes.player.domain.usecase.SeekToNextUseCase
import com.yes.player.domain.usecase.SeekToPreviousUseCase
import com.yes.player.domain.usecase.SeekUseCase
import com.yes.player.domain.usecase.SubscribeCurrentPlaylistUseCase
import com.yes.player.domain.usecase.SubscribePlayerStateUseCase
import com.yes.player.domain.usecase.SubscribeDurationCounterUseCase
import com.yes.player.domain.usecase.SubscribeVisualizerUseCase
import com.yes.player.presentation.mapper.MapperUI
import com.yes.player.presentation.ui.PlayerScreen
import com.yes.player.presentation.vm.PlayerViewModel
import dagger.Module
import dagger.Provides

@Module
class PlayerModule {
    @Provides
    fun providesVisualizer(
        audioSessionId: Int
    ): Visualizer {
        var visualizer=Visualizer( audioSessionId)
       if (visualizer.enabled){
           visualizer.release()
       }
        visualizer=Visualizer( audioSessionId)
        val e =visualizer.enabled
        visualizer.enabled = false
        return visualizer
    }
    @Provides
    fun providesVisualizerRepository(
        visualizer: Visualizer
    ): VisualizerRepository {
        return VisualizerRepository(
            visualizer
        )
    }

    @Provides
    fun providesSettingsRepository(
        dataStore: SettingsDataStore
    ): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(
            dataStore
        )
    }

    @Provides
    fun providesMapper(): Mapper {
        return Mapper()
    }


    @Provides
    fun providesPlayListRepository(
        mapper: Mapper,
        playListDao: IPlayListDao,
    ): PlaylistRepositoryImpl {
        return PlaylistRepositoryImpl(
            mapper,
            playListDao,
        )
    }


    @Provides
    fun providesPlayerRepository(
        playerDataSource: PlayerDataSource,
        mapper: Mapper
    ): PlayerRepository {
        return PlayerRepository(
            playerDataSource,
            mapper
        )
    }


    @Provides
    fun providesMapperUI(): MapperUI {
        return MapperUI()
    }




    @Provides
    fun providesPlayerViewModelFactory(
        mapperUI: MapperUI,
        playUseCase: PlayUseCase,
        subscribeDurationCounterUseCase: SubscribeDurationCounterUseCase,
        seekToNextUseCase: SeekToNextUseCase,
        seekToPreviousUseCase: SeekToPreviousUseCase,
        subscribeCurrentPlaylistUseCase: SubscribeCurrentPlaylistUseCase,
        subscribePlayerStateUseCase: SubscribePlayerStateUseCase,
        seekUseCase: SeekUseCase,
       subscribeVisualizerUseCase: SubscribeVisualizerUseCase,


    ): PlayerViewModel.Factory {
        return PlayerViewModel.Factory(
            mapperUI,
            playUseCase,
            subscribeDurationCounterUseCase,
            seekToNextUseCase,
            seekToPreviousUseCase,
            subscribeCurrentPlaylistUseCase,
            subscribePlayerStateUseCase,
            seekUseCase,
            subscribeVisualizerUseCase,

        )
    }

    @Provides
    fun providesDependency(
        factory: PlayerViewModel.Factory,
    ): PlayerScreen.Dependency {
        return PlayerScreen.Dependency(
            factory
        )
    }
}