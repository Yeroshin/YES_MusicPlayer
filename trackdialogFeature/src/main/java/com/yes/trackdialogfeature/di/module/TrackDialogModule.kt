package com.yes.trackdialogfeature.di.module

import com.yes.core.di.module.IoDispatcher
import com.yes.core.data.dataSource.MediaDataStore
import com.yes.core.util.EspressoIdlingResource
import com.yes.trackdialogfeature.data.mapper.MediaRepositoryMapper
import com.yes.trackdialogfeature.data.mapper.MenuRepositoryMapper
import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.data.repository.MenuRepositoryImpl
import com.yes.trackdialogfeature.data.repository.SettingsRepositoryImpl
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore

import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.trackdialogfeature.data.repository.NetworkRepository
import com.yes.trackdialogfeature.data.repository.dataSource.NetworkDataSource
import com.yes.trackdialogfeature.domain.usecase.CheckNetworkPathAvailableUseCase
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.ui.TrackDialog.Dependency
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import java.util.ArrayDeque

@Module
class TrackDialogModule {

    @Provides
    fun providesTrackDialogDependency(factory: TrackDialogViewModel.Factory): Dependency {
        return Dependency(
            factory
        )
    }

    @Provides
    fun providesMenuRepositoryMapper(): MenuRepositoryMapper {
        return MenuRepositoryMapper()
    }

    @Provides
    fun providesMenuDataStore(): MenuDataStore {
        return MenuDataStore()
    }

    @Provides
    fun providesMenuRepository(
        menuRepositoryMapper: MenuRepositoryMapper,
        menuDataStore: MenuDataStore,
    ): MenuRepositoryImpl {
        return MenuRepositoryImpl(
            menuRepositoryMapper,
            menuDataStore,
        )
    }

    @Provides
    fun providesMediaRepositoryMapper(): MediaRepositoryMapper {
        return MediaRepositoryMapper()
    }

    @Provides
    fun providesMediaRepository(
        mediaDataStore: MediaDataStore,
        mediaRepositoryMapper: MediaRepositoryMapper,
    ): MediaRepositoryImpl {
        return MediaRepositoryImpl(
            mediaDataStore,
            mediaRepositoryMapper,
        )
    }

    @Provides
    fun providesGetMenuUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        menuRepository: MenuRepositoryImpl,
        mediaRepository: MediaRepositoryImpl
    ): GetMenuUseCase {
        return GetMenuUseCase(
            dispatcher,
            menuRepository,
            mediaRepository,
        )
    }
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
        settings: SettingsDataStore
    ): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(
            settings
        )
    }

    @Provides
    fun providesSaveTracksToPlaylistUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        mediaRepositoryImpl: MediaRepositoryImpl,
        playListRepository: IPlayListDao,
        settingsRepository: SettingsRepositoryImpl,
        menuRepository: MenuRepositoryImpl
    ): SaveTracksToPlaylistUseCase {
        return SaveTracksToPlaylistUseCase(
            dispatcher,
            mediaRepositoryImpl,
            playListRepository,
            settingsRepository,
            menuRepository
        )
    }

    @Provides
    fun providesUiMapper(): UiMapper {
        return UiMapper()
    }

    @Provides
    fun providesArrayDeque(): ArrayDeque<MenuUi> {
        return ArrayDeque()
    }
    @Provides
    fun providesNetworkDataSource(): NetworkDataSource {
        return NetworkDataSource()
    }
    @Provides
    fun providesNetworkRepository(
        networkDataSource: NetworkDataSource
    ): NetworkRepository {
        return NetworkRepository(networkDataSource)
    }
    @Provides
    fun providesCheckNetworkPathAvailableUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        networkRepository: NetworkRepository
    ): CheckNetworkPathAvailableUseCase {
        return CheckNetworkPathAvailableUseCase(
            dispatcher,
            networkRepository
        )
    }

    @Provides
    fun providesTrackDialogViewModelFactory(
        getMenuUseCase: GetMenuUseCase,
        saveTracksToPlaylistUseCase: SaveTracksToPlaylistUseCase,
        uiMapper: UiMapper,
        menuStack: ArrayDeque<MenuUi>,
        espressoIdlingResource: EspressoIdlingResource?,
        checkNetworkPathAvailableUseCase: CheckNetworkPathAvailableUseCase
    ): TrackDialogViewModel.Factory {
        return TrackDialogViewModel.Factory(
            getMenuUseCase,
            saveTracksToPlaylistUseCase,
            uiMapper,
            menuStack,
            espressoIdlingResource,
            checkNetworkPathAvailableUseCase
        )
    }


}