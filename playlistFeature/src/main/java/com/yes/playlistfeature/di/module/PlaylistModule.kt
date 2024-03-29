package com.yes.playlistfeature.di.module


import com.yes.core.di.module.IoDispatcher
import com.yes.core.di.module.MainDispatcher
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.data.dataSource.PlayerDataSource
import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.core.util.EspressoIdlingResource
import com.yes.playlistfeature.data.mapper.Mapper
import com.yes.playlistfeature.data.repository.PlayListRepositoryImpl
import com.yes.playlistfeature.data.repository.PlayerRepository
import com.yes.playlistfeature.data.repository.SettingsRepositoryImpl
import com.yes.playlistfeature.domain.usecase.ChangeTracksPositionUseCase
import com.yes.playlistfeature.domain.usecase.DeleteTrackUseCase
import com.yes.playlistfeature.domain.usecase.SetModeUseCase
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
class PlaylistModule {
   /* @Provides
    fun providesSettingsDataStore(
        dataStore: DataStore<Preferences>
    ): SettingsDataStore {
        return SettingsDataStore(
            dataStore
        )
    }*/
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
        @IoDispatcher dispatcher: CoroutineDispatcher,
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
        @IoDispatcher dispatcher: CoroutineDispatcher,
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
    fun providesChangeTracksPositionUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        playListRepositoryImpl: PlayListRepositoryImpl,
        settingsRepository: SettingsRepositoryImpl

    ): ChangeTracksPositionUseCase {
        return ChangeTracksPositionUseCase(
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
        @MainDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository,
        ): SetTracksToPlayerPlaylistUseCase {
        return SetTracksToPlayerPlaylistUseCase(
            dispatcher,
            playerRepository
        )
    }
    @Provides
    fun providesSetModeUseCase(
        @MainDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository,
    ): SetModeUseCase {
        return SetModeUseCase(
            dispatcher,
            playerRepository
        )
    }
    @Provides
    fun providesPlaylistViewModelFactory(
        espressoIdlingResource: EspressoIdlingResource?,
        subscribeCurrentPlaylistTracksUseCase: SubscribeCurrentPlaylistTracksUseCase,
        mapperUI: MapperUI,
        deleteTrackUseCase: DeleteTrackUseCase,
        setTracksToPlayerPlaylistUseCase: SetTracksToPlayerPlaylistUseCase,
        setModeUseCase:SetModeUseCase,
        changeTracksPositionUseCase:ChangeTracksPositionUseCase
    ): PlaylistViewModel.Factory {
        return PlaylistViewModel.Factory(
            espressoIdlingResource,
            subscribeCurrentPlaylistTracksUseCase,
            mapperUI,
            deleteTrackUseCase,
            setTracksToPlayerPlaylistUseCase,
            setModeUseCase,
            changeTracksPositionUseCase
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