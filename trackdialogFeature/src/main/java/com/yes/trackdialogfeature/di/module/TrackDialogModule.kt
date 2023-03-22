package com.yes.trackdialogfeature.di.module

import android.app.Activity
//import com.yes.trackdialogfeature.data.repository.MediaDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.mapper.MediaMapper
import com.yes.trackdialogfeature.data.mapper.MediaQueryMapper
import com.yes.trackdialogfeature.data.repository.MenuRepositoryImpl
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.presentation.ui.TrackDialogAdapter
import com.yes.trackdialogfeature.presentation.mapper.MenuDomainUiMapper
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
    ): MenuRepositoryImpl {
        return MenuRepositoryImpl(menuDataStore,audioDataStore)
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
    fun provideGetChildMenu(
        menuRepository: MenuRepositoryImpl
    ): GetChildMenuUseCase {
        return GetChildMenuUseCase(Dispatchers.IO,menuRepository)
    }
    @Provides
    fun provideUIMapper(): MenuDomainUiMapper {
        return MenuDomainUiMapper()
    }
    @Provides
    fun provideViewModelFactory(
        getChildMenuUseCase: GetChildMenuUseCase,
        menuDomainUiMapper:MenuDomainUiMapper
    ): TrackDialogViewModel.Factory {
        return TrackDialogViewModel.Factory(getChildMenuUseCase,menuDomainUiMapper)
    }
    @Provides
    fun provideTrackDialogAdapter(): TrackDialogAdapter {
        return TrackDialogAdapter()
    }
}