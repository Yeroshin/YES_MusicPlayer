package com.yes.player.di.module

import com.yes.core.di.module.IoDispatcher
import com.yes.core.di.module.MainDispatcher
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.repository.dataSource.PlayerDataSource
import com.yes.core.repository.dataSource.SettingsDataStore
import com.yes.player.data.mapper.Mapper
import com.yes.player.data.repository.PlayerRepository
import com.yes.player.data.repository.PlaylistRepositoryImpl
import com.yes.player.data.repository.SettingsRepositoryImpl
import com.yes.player.domain.usecase.PlayUseCase
import com.yes.player.domain.usecase.SeekToNextUseCase
import com.yes.player.domain.usecase.SeekToPreviousUseCase
import com.yes.player.domain.usecase.SeekUseCase
import com.yes.player.domain.usecase.SubscribeCurrentPlaylistUseCase
import com.yes.player.domain.usecase.SubscribePlayerStateUseCase
import com.yes.player.domain.usecase.SubscribeDurationCounterUseCase
import com.yes.player.presentation.mapper.MapperUI
import com.yes.player.presentation.ui.PlayerFragment
import com.yes.player.presentation.vm.PlayerViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class PlayerModule {
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
    fun providesSubscribeDurationCounterUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): SubscribeDurationCounterUseCase {
        return SubscribeDurationCounterUseCase(
            dispatcher,
            playerRepository
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
    fun providesPlayUseCase(
        @MainDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): PlayUseCase {
        return PlayUseCase(
            dispatcher,
            playerRepository
        )
    }

    @Provides
    fun providesMapperUI(): MapperUI {
        return MapperUI()
    }

    @Provides
    fun providesSeekToNextUseCase(
        @MainDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): SeekToNextUseCase {
        return SeekToNextUseCase(
            dispatcher,
            playerRepository
        )
    }

    @Provides
    fun providesSeekToPreviousUseCase(
        @MainDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): SeekToPreviousUseCase {
        return SeekToPreviousUseCase(
            dispatcher,
            playerRepository
        )
    }

    @Provides
    fun providesSubscribeCurrentPlaylistUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        playListRepositoryImpl: PlaylistRepositoryImpl,
        settingsRepository: SettingsRepositoryImpl
    ): SubscribeCurrentPlaylistUseCase {
        return SubscribeCurrentPlaylistUseCase(
            dispatcher,
            playListRepositoryImpl,
            settingsRepository
        )
    }
    @Provides
    fun providesSubscribeCurrentTrackInfoUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): SubscribePlayerStateUseCase {
        return SubscribePlayerStateUseCase(
            dispatcher,
            playerRepository
        )
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
        seekUseCase: SeekUseCase
    ): PlayerViewModel.Factory {
        return PlayerViewModel.Factory(
            mapperUI,
            playUseCase,
            subscribeDurationCounterUseCase,
            seekToNextUseCase,
            seekToPreviousUseCase,
            subscribeCurrentPlaylistUseCase,
            subscribePlayerStateUseCase,
            seekUseCase
        )
    }

    @Provides
    fun providesDependency(
        factory: PlayerViewModel.Factory,
    ): PlayerFragment.Dependency {
        return PlayerFragment.Dependency(
            factory
        )
    }
}