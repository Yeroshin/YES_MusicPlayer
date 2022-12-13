package com.yes.trackdiialogfeature.di.module

import android.app.Activity
import com.yes.trackdiialogfeature.data.repository.MediaRepository
import com.yes.trackdiialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdiialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdiialogfeature.data.mapper.MediaMapper
import com.yes.trackdiialogfeature.data.mapper.MediaQueryMapper
import com.yes.trackdiialogfeature.data.repository.MenuRepository
import com.yes.trackdiialogfeature.domain.*
import com.yes.trackdiialogfeature.domain.usecase.GetRootMenu
import com.yes.trackdiialogfeature.domain.usecase.ShowChildMenu
import com.yes.trackdiialogfeature.presentation.TrackDialogAdapter
import com.yes.trackdiialogfeature.presentation.TrackDialogViewModelFactory
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
    ): ShowChildMenu {
        return ShowChildMenu(menuRepository)
    }

    @Provides
    fun provideGetRootMenu(menuRepository: MenuRepository): GetRootMenu {
        return GetRootMenu(menuRepository)
    }

    @Provides
    fun provideViewModelFactory(
        showChildMenu: ShowChildMenu,
        getRootMenu: GetRootMenu
    ): TrackDialogViewModelFactory {
        return TrackDialogViewModelFactory(showChildMenu, getRootMenu)
    }

    @Provides
    fun provideMediaDialogAdapter(): TrackDialogAdapter {
        return TrackDialogAdapter()
    }
}