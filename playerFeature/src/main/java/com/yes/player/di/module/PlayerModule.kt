package com.yes.player.di.module

import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.repository.dataSource.SettingsDataStore
import com.yes.player.data.mapper.Mapper
import com.yes.player.data.repository.PlaylistRepositoryImpl
import com.yes.player.data.repository.SettingsRepositoryImpl
import com.yes.player.domain.usecase.SubscribeCurrentPlaylistTracksUseCase
import com.yes.player.presentation.MusicService
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
    fun providesServiceMapper(): com.yes.player.presentation.mapper.Mapper {
        return com.yes.player.presentation.mapper.Mapper()
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
    fun providesSubscribeCurrentPlaylistTracksUseCase(
        dispatcher: CoroutineDispatcher,
        playListRepositoryImpl: PlaylistRepositoryImpl,
        settingsRepository: SettingsRepositoryImpl
    ): SubscribeCurrentPlaylistTracksUseCase {
        return SubscribeCurrentPlaylistTracksUseCase(
            dispatcher,
            playListRepositoryImpl,
            settingsRepository
        )
    }

    @Provides
    fun providesDependency(
        mapper: com.yes.player.presentation.mapper.Mapper,
        subscribeCurrentPlaylistTracksUseCase: SubscribeCurrentPlaylistTracksUseCase
    ): MusicService.Dependency {
        return MusicService.Dependency(
            mapper,
            subscribeCurrentPlaylistTracksUseCase
        )
    }
}