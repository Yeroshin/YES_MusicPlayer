package com.yes.playlistdialogfeature.di.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModelProvider
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.util.EspressoIdlingResource
import com.yes.playlistdialogfeature.data.mapper.Mapper
import com.yes.playlistdialogfeature.data.repository.PlayListDialogRepositoryImpl
import com.yes.playlistdialogfeature.data.repository.SettingsRepositoryImpl
import com.yes.playlistdialogfeature.domain.usecase.AddPlayListUseCase
import com.yes.playlistdialogfeature.domain.usecase.DeletePlayListUseCase
import com.yes.playlistdialogfeature.domain.usecase.SetPlaylistUseCase
import com.yes.playlistdialogfeature.domain.usecase.SubscribePlayListsUseCase
import com.yes.playlistdialogfeature.presentation.mapper.UiMapper
import com.yes.playlistdialogfeature.presentation.ui.PlayListDialog
import com.yes.playlistdialogfeature.presentation.vm.PlayListDialogViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module
class PlayListDialogModule {
    @Provides
    fun providesPlayListDialogDependency(factory: PlayListDialogViewModel.Factory): PlayListDialog.Dependency {
        return PlayListDialog.Dependency(
            factory
        )
    }



    @Provides
    fun providesMapper(): Mapper {
        return Mapper()
    }

    @Provides
    fun providesPlayListDialogRepository(
        mapper: Mapper,
        playListDao: IPlayListDao,
    ): PlayListDialogRepositoryImpl {
        return PlayListDialogRepositoryImpl(
            mapper,
            playListDao,
        )
    }

    @Provides
    fun providesSubscribePlayListsUseCase(
        dispatcher: CoroutineDispatcher,
        playListDialogRepositoryImpl: PlayListDialogRepositoryImpl,
        settingsRepository: SettingsRepositoryImpl
    ): SubscribePlayListsUseCase {
        return SubscribePlayListsUseCase(
            dispatcher,
            playListDialogRepositoryImpl,
            settingsRepository
        )
    }

    @Provides
    fun providesAddPlayListUseCase(
        dispatcher: CoroutineDispatcher,
        playListDialogRepositoryImpl: PlayListDialogRepositoryImpl,
        settingsRepository: SettingsRepositoryImpl
    ): AddPlayListUseCase {
        return AddPlayListUseCase(
            dispatcher,
            playListDialogRepositoryImpl,
            settingsRepository
        )
    }

    @Provides
    fun providesDeletePlayListUseCase(
        dispatcher: CoroutineDispatcher,
    ): DeletePlayListUseCase {
        return DeletePlayListUseCase(
            dispatcher
        )
    }

    @Provides
    fun providesSetPlaylistUseCase(
        dispatcher: CoroutineDispatcher,
        settingsRepository: SettingsRepositoryImpl
    ): SetPlaylistUseCase {
        return SetPlaylistUseCase(
            dispatcher,
            settingsRepository
        )
    }

    @Provides
    fun providesUiMapper(): UiMapper {
        return UiMapper()
    }
    @Provides
    fun providesSettingsRepository(
        dataStore: DataStore<Preferences>
    ): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(
            dataStore
        )
    }


    @Provides
    fun providesPlayListDialogViewModelFactory(
        subscribePlayLists: SubscribePlayListsUseCase,
        addPlayListUseCase: AddPlayListUseCase,
        deletePlayListUseCase: DeletePlayListUseCase,
        setPlaylistUseCase: SetPlaylistUseCase,
        uiMapper: UiMapper,
        espressoIdlingResource: EspressoIdlingResource?
    ): PlayListDialogViewModel.Factory {
        return PlayListDialogViewModel.Factory(
            subscribePlayLists,
            addPlayListUseCase,
            deletePlayListUseCase,
            setPlaylistUseCase,
            uiMapper,
            espressoIdlingResource
        )
    }
}