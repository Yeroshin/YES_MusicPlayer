package com.yes.playlistfeature.di.module


import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.repository.dataSource.PlayerDataSource
import com.yes.core.repository.dataSource.SettingsDataStore
import com.yes.core.util.EspressoIdlingResource
import com.yes.playlistfeature.data.mapper.Mapper
import com.yes.playlistfeature.data.repository.PlayListRepositoryImpl
import com.yes.playlistfeature.data.repository.PlayerRepository
import com.yes.playlistfeature.data.repository.SettingsRepositoryImpl
import com.yes.playlistfeature.domain.usecase.DeleteTrackUseCase
import com.yes.playlistfeature.domain.usecase.SetTracksToPlayerPlaylistUseCase
import com.yes.playlistfeature.domain.usecase.SubscribeCurrentPlaylistTracksUseCase
import com.yes.playlistfeature.presentation.mapper.MapperUI
import com.yes.playlistfeature.presentation.ui.Playlist
import com.yes.playlistfeature.presentation.ui.PlaylistAdapter
import com.yes.playlistfeature.presentation.vm.PlaylistViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class PlayListModule {
    @Provides
    fun providesSettingsRepository(
        dataStore: SettingsDataStore
    ): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(
            dataStore
        )
    }

    @Provides
    fun providesDataMapper(): Mapper {
        return Mapper()
    }

    @Provides
    fun providesPlayListRepository(
        mapper: Mapper,
        playListDao: IPlayListDao,
    ): PlayListRepositoryImpl {
        return PlayListRepositoryImpl(
            mapper,
            playListDao,
        )
    }

    @Provides
    fun providesMapperUI(): MapperUI {
        return MapperUI()
    }

    @Provides
    fun providesSubscribeCurrentPlaylistTracksUseCase(
        dispatcher: CoroutineDispatcher,
        playListRepositoryImpl: PlayListRepositoryImpl,
        settingsRepository: SettingsRepositoryImpl
    ): SubscribeCurrentPlaylistTracksUseCase {
        return SubscribeCurrentPlaylistTracksUseCase(
            dispatcher,
            playListRepositoryImpl,
            settingsRepository
        )
    }

    @Provides
    fun providesDeleteTrackUseCase(
        dispatcher: CoroutineDispatcher,
        playListRepositoryImpl: PlayListRepositoryImpl,
        settingsRepository: SettingsRepositoryImpl

    ): DeleteTrackUseCase {
        return DeleteTrackUseCase(
            dispatcher,
            playListRepositoryImpl,
            settingsRepository
        )
    }
    @Provides
    fun providesPlayerRepository(
        mapper:Mapper,
        playerDataSource: PlayerDataSource
    ): PlayerRepository {
        return PlayerRepository(
            mapper,
            playerDataSource
        )
    }
    @Provides
    fun providesSetTracksToPlayerPlaylistUseCase(
        playerRepository: PlayerRepository,
        ): SetTracksToPlayerPlaylistUseCase {
        return SetTracksToPlayerPlaylistUseCase(
            playerRepository
        )
    }
    @Provides
    fun providesPlaylistViewModelFactory(
        espressoIdlingResource: EspressoIdlingResource?,
        subscribeCurrentPlaylistTracksUseCase: SubscribeCurrentPlaylistTracksUseCase,
        mapperUI: MapperUI,
        deleteTrackUseCase: DeleteTrackUseCase,
        setTracksToPlayerPlaylistUseCase: SetTracksToPlayerPlaylistUseCase

    ): PlaylistViewModel.Factory {
        return PlaylistViewModel.Factory(
            espressoIdlingResource,
            subscribeCurrentPlaylistTracksUseCase,
            mapperUI,
            deleteTrackUseCase,
            setTracksToPlayerPlaylistUseCase
        )
    }

    @Provides
    fun providesPlayListAdapter(): PlaylistAdapter {
        return PlaylistAdapter()
    }

    @Provides
    fun providesPlayListDialogDependency(
        factory: PlaylistViewModel.Factory,
        adapter: PlaylistAdapter
    ): Playlist.Dependency {
        return Playlist.Dependency(
            factory,
            adapter
        )
    }
}