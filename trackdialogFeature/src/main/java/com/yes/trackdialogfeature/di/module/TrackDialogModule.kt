package com.yes.trackdialogfeature.di.module

import android.app.Activity
//import com.yes.trackdialogfeature.data.repository.MediaDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.mapper.MediaMapper
import com.yes.trackdialogfeature.data.mapper.MediaQueryMapper
import com.yes.trackdialogfeature.data.repository.MenuRepository
import com.yes.trackdialogfeature.domain.*
import com.yes.trackdialogfeature.domain.usecase.GetRootMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.presentation.ui.TrackDialogAdapter
import com.yes.trackdialogfeature.presentation.mapper.MenuMapper
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class TrackDialogModule(val context: Activity) {
    @Provides
    fun provideContext(): Activity {
        return context
    }

    @Provides
    fun providesMenuDataStore(): MenuDataStore {
        return MenuDataStore()
    }

    @Provides
    fun providesMenuRepository(
        menuDataStore: MenuDataStore,
        audioDataStore: AudioDataStore
    ): MenuRepository {
        return MenuRepository(menuDataStore,audioDataStore)
    }

    @Provides
    fun provideMediaMapper(): MediaMapper {
        return MediaMapper()
    }

    @Provides
    fun provideMediaQueryMapper(): MediaQueryMapper {
        return MediaQueryMapper()
    }

    ///////////////////////////
    @Provides
    fun providesMediaDataStore(): AudioDataStore {
        return AudioDataStore(context)
    }


  /*  @Provides
    fun provideMediaRepository(
        audioDataStore: AudioDataStore,
        mediaMapper: MediaMapper,
        mediaQueryMapper: MediaQueryMapper

    ): IMediaDataStore {
        return MediaDataStore(audioDataStore, mediaMapper, mediaQueryMapper)
    }*/


    @Provides
    fun provideShowChildMenu(
        menuRepository: MenuRepository
    ): GetChildMenuUseCase {

        return GetChildMenuUseCase(Dispatchers.IO,menuRepository)
    }

    @Provides
    fun provideGetRootMenuUseCase(menuRepository: MenuRepository): GetRootMenuUseCase {
        return GetRootMenuUseCase(Dispatchers.IO,menuRepository)
    }
    @Provides
    fun provideUIMapper(): MenuMapper {
        return MenuMapper()
    }
    @Provides
    fun provideViewModelFactory(
        getRootMenuUseCase: GetRootMenuUseCase,
        getChildMenuUseCase: GetChildMenuUseCase,
        menuMapper:MenuMapper
    ): TrackDialogViewModel.Factory {
        return TrackDialogViewModel.Factory(getRootMenuUseCase,getChildMenuUseCase,menuMapper)
    }

    @Provides
    fun provideTrackDialogAdapter(): TrackDialogAdapter {
        return TrackDialogAdapter()
    }
}