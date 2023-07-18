package com.yes.trackdialogfeature.di.module

import android.app.Activity
import com.yes.trackdialogfeature.data.repository.MenuRepositoryImpl
//import com.yes.trackdialogfeature.data.repository.MediaDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MediaDataStore
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase
import com.yes.trackdialogfeature.presentation.ui.TrackDialogAdapter
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import dagger.Module
import dagger.Provides
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





    ///////////////////////////
    @Provides
    fun providesMediaDataStore(): MediaDataStore {
        return MediaDataStore(context)
    }

    @Provides
    fun provideUIMapper(): UiMapper {
        return UiMapper()
    }
    @Provides
    fun provideMenuStack(): ArrayDeque<MenuUi> {
        return ArrayDeque()
    }

    @Provides
    fun provideViewModelFactory(
        getMenuUseCase: GetMenuUseCase,
        saveTracksToPlaylistUseCase: SaveTracksToPlaylistUseCase,
        uiMapper:UiMapper,
        menuStack: ArrayDeque<MenuUi>,
    ): TrackDialogViewModel.Factory {
        return TrackDialogViewModel.Factory(
            getMenuUseCase,
            saveTracksToPlaylistUseCase,
            uiMapper,
            menuStack
        )
    }
    @Provides
    fun provideTrackDialogAdapter(): TrackDialogAdapter {
        return TrackDialogAdapter()
    }
}