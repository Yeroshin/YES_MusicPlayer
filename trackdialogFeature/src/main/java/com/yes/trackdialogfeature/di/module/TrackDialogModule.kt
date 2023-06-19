package com.yes.trackdialogfeature.di.module

import android.app.Activity
//import com.yes.trackdialogfeature.data.repository.MediaDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.mapper.MediaMapper
import com.yes.trackdialogfeature.data.mapper.MediaQueryMapper
import com.yes.trackdialogfeature.data.repository.MenuRepositoryImplOLD
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCaseOLD
import com.yes.trackdialogfeature.presentation.ui.TrackDialogAdapter
import com.yes.trackdialogfeature.presentation.mapper.MenuUiDomainMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import java.util.ArrayDeque

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
    ): MenuRepositoryImplOLD {
        return MenuRepositoryImplOLD(menuDataStore,audioDataStore)
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
        menuRepository: MenuRepositoryImplOLD
    ): GetChildMenuUseCaseOLD {
        return GetChildMenuUseCaseOLD(Dispatchers.IO,menuRepository)
    }
    @Provides
    fun provideUIMapper(): MenuUiDomainMapper {
        return MenuUiDomainMapper()
    }
    @Provides
    fun provideMenuStack(): ArrayDeque<MenuUi> {
        return ArrayDeque()
    }

    @Provides
    fun provideViewModelFactory(
        getChildMenuUseCaseOLD: GetChildMenuUseCaseOLD,
        menuUiDomainMapper:MenuUiDomainMapper,
        menuStack: ArrayDeque<MenuUi>
    ): TrackDialogViewModel.Factory {
        return TrackDialogViewModel.Factory(getChildMenuUseCaseOLD,menuUiDomainMapper,menuStack)
    }
    @Provides
    fun provideTrackDialogAdapter(): TrackDialogAdapter {
        return TrackDialogAdapter()
    }
}