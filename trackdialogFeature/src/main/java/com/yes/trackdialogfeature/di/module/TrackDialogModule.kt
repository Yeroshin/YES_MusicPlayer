package com.yes.trackdialogfeature.di.module

import android.app.Activity
import com.yes.trackdialogfeature.data.repository.MediaRepository
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.mapper.MediaMapper
import com.yes.trackdialogfeature.data.mapper.MediaQueryMapper
import com.yes.trackdialogfeature.data.repository.MenuRepository
import com.yes.trackdialogfeature.domain.*
import com.yes.trackdialogfeature.domain.usecase.GetRootMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.ShowChildMenuUseCase
import com.yes.trackdialogfeature.presentation.ui.TrackDialogAdapter
import com.yes.trackdialogfeature.presentation.mapper.MenuMapper
import dagger.Module
import dagger.Provides

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
        mediaRepository: IMediaRepository
    ): MenuRepository {
        return MenuRepository(menuDataStore,mediaRepository)
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


    @Provides
    fun provideMediaRepository(
        audioDataStore: AudioDataStore,
        mediaMapper: MediaMapper,
        mediaQueryMapper: MediaQueryMapper

    ): IMediaRepository {
        return MediaRepository(audioDataStore, mediaMapper, mediaQueryMapper)
    }


    @Provides
    fun provideShowChildMenu(
        menuRepository: MenuRepository
    ): ShowChildMenuUseCase {
        return ShowChildMenuUseCase(menuRepository)
    }

    @Provides
    fun provideGetRootMenu(menuRepository: MenuRepository): GetRootMenuUseCase {
        return GetRootMenuUseCase(menuRepository)
    }
    @Provides
    fun provideUIMapper(): MenuMapper {
        return MenuMapper()
    }
   /* @Provides
    fun provideViewModelFactory(
        getRootMenuUseCase: GetRootMenuUseCase,
        showChildMenuUseCase: ShowChildMenuUseCase,
        menuMapper:MenuMapper
    ): TrackDialogViewModelFactory {
        return TrackDialogViewModelFactory(getRootMenuUseCase,showChildMenuUseCase,menuMapper)
    }*/

    @Provides
    fun provideMediaDialogAdapter(): TrackDialogAdapter {
        return TrackDialogAdapter()
    }
}