package com.yes.player.di.module

import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.repository.dataSource.PlayerDataSource
import com.yes.core.repository.dataSource.SettingsDataStore
import com.yes.player.data.mapper.Mapper
import com.yes.player.data.repository.PlayerRepository
import com.yes.player.data.repository.PlaylistRepositoryImpl
import com.yes.player.data.repository.SettingsRepositoryImpl
import com.yes.player.domain.usecase.PlayUseCase
import com.yes.player.domain.usecase.SubscribeCurrentPlaylistUseCase
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
        dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): SubscribeDurationCounterUseCase {
        return SubscribeDurationCounterUseCase(
            dispatcher,
            playerRepository
        )
    }
    @Provides
    fun providesPlayerRepository(
        playerDataSource: PlayerDataSource
    ): PlayerRepository{
        return PlayerRepository(
            playerDataSource
        )
    }
    @Provides
    fun providesPlayUseCase(
        playerRepository: PlayerRepository
    ): PlayUseCase {
        return PlayUseCase(
            playerRepository
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
        subscribeDurationCounterUseCase: SubscribeDurationCounterUseCase
    ): PlayerViewModel.Factory {
        return PlayerViewModel.Factory(
            mapperUI,
            playUseCase,
            subscribeDurationCounterUseCase
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