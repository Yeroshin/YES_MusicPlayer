package com.yes.trackdialogfeature.di.module

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.yes.trackdialogfeature.TrackDialogTest
import com.yes.trackdialogfeature.data.mapper.MediaRepositoryMapper
import com.yes.trackdialogfeature.data.mapper.MenuRepositoryMapper
import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.data.repository.MenuRepositoryImpl
import com.yes.trackdialogfeature.data.repository.SettingsRepositoryImpl
import com.yes.trackdialogfeature.data.repository.dataSource.MediaDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.PlayListDataBase
import com.yes.trackdialogfeature.data.repository.dataSource.SettingsDataStore
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.repository.IPlayListDao
import com.yes.trackdialogfeature.domain.repository.ISettingsRepository
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase
import com.yes.trackdialogfeature.domain.usecase.UseCase
import com.yes.trackdialogfeature.presentation.TrackDialogEndToEndTest
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import dagger.Module
import dagger.Provides

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import java.util.ArrayDeque

@Module
class TestTrackDialogModule(

) {


    @Provides
    fun providesCoroutineDispatcher(): CoroutineDispatcher {
        //return StandardTestDispatcher()
        return Dispatchers.IO
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
        dispatcher: CoroutineDispatcher,
        menuRepository: MenuRepositoryImpl,
        mediaRepository: MediaRepositoryImpl
    ): GetMenuUseCase {
        return GetMenuUseCase(
            dispatcher,
            menuRepository,
            mediaRepository,
        )
    }

    @Provides
    fun providesSettingsRepository(
        settingsDataStore: SettingsDataStore
    ): ISettingsRepository{
        return SettingsRepositoryImpl(
            settingsDataStore
        )
    }

    @Provides
    fun providesSaveTracksToPlaylistUseCase(
        dispatcher: CoroutineDispatcher,
        mediaRepositoryImpl: MediaRepositoryImpl,
        playListRepository: IPlayListDao,
        settingsRepository: ISettingsRepository
    ): SaveTracksToPlaylistUseCase {
        return SaveTracksToPlaylistUseCase(
            dispatcher,
            mediaRepositoryImpl,
            playListRepository,
            settingsRepository
        )
    }
    @Provides
    fun providesUiMapper():UiMapper{
        return UiMapper()
    }
    @Provides
    fun providesArrayDeque(): ArrayDeque<MenuUi>{
        return ArrayDeque()
    }

    @Provides
    fun providesTrackDialogViewModelFactory(
        getMenuUseCase: GetMenuUseCase,
        saveTracksToPlaylistUseCase: SaveTracksToPlaylistUseCase,
        uiMapper: UiMapper,
        menuStack: ArrayDeque<MenuUi>,
    ): ViewModelProvider.Factory {
        return TrackDialogViewModel.Factory(
            getMenuUseCase,
            saveTracksToPlaylistUseCase,
            uiMapper,
            menuStack,
        )
    }



}