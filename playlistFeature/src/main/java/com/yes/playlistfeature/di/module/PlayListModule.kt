package com.yes.playlistfeature.di.module


import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.repository.dataSource.SettingsDataStore
import com.yes.core.util.EspressoIdlingResource
import com.yes.playlistfeature.data.repository.PlayListRepositoryImpl
import com.yes.playlistfeature.data.repository.SettingsRepositoryImpl
import com.yes.playlistfeature.domain.usecase.SubscribeCurrentPlaylistTracksUseCase
import com.yes.playlistfeature.presentation.mapper.Mapper
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
    ):SettingsRepositoryImpl {
        return SettingsRepositoryImpl(
            dataStore
        )
    }
    @Provides
    fun providesPlayListRepository(
        mapper: com.yes.playlistfeature.data.mapper.Mapper,
        playListDao: IPlayListDao,
    ): PlayListRepositoryImpl {
        return PlayListRepositoryImpl(
            mapper,
            playListDao,
        )
    }

    @Provides
    fun providesMapper(): Mapper {
        return Mapper()
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
    fun providesPlaylistViewModelFactory(
        espressoIdlingResource: EspressoIdlingResource?,
        subscribeCurrentPlaylistTracksUseCase: SubscribeCurrentPlaylistTracksUseCase,
        mapper: Mapper
    ): PlaylistViewModel.Factory {
        return PlaylistViewModel.Factory(
            espressoIdlingResource,
            subscribeCurrentPlaylistTracksUseCase,
            mapper
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