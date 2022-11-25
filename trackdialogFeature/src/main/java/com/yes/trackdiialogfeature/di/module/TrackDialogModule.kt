package com.yes.trackdiialogfeature.di.module

import android.app.Activity
import com.yes.trackdiialogfeature.data.repository.MediaRepository
import com.yes.trackdiialogfeature.data.repository.dataSource.CategoryDataStore
import com.yes.trackdiialogfeature.data.repository.dataSource.MediaDataStore
import com.yes.trackdiialogfeature.data.mapper.MediaMapper
import com.yes.trackdiialogfeature.domain.*
import com.yes.trackdiialogfeature.ui.TrackDialogAdapter
import com.yes.trackdiialogfeature.ui.TrackDialogViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TrackDialogModule(val context: Activity) {
    @Provides
    fun provideContext(): Activity {
        return context
    }

    @Provides
    fun providesCategoryDataStore(): CategoryDataStore {
        return CategoryDataStore(context)
    }

    @Provides
    fun provideMediaMapper(): MediaMapper {
        return MediaMapper()
    }

    ///////////////////////////
    @Provides
    fun providesMediaDataStore(): MediaDataStore {
        return MediaDataStore(context)
    }


    @Provides
    fun provideMediaRepository(
        mediaDataStore: MediaDataStore,
        categoryDataStore: CategoryDataStore,
        mediaMapper: MediaMapper
    ): IMenuRepository {
        return MediaRepository(mediaDataStore, categoryDataStore, mediaMapper)
    }

    /////////////////////////
   /* @Provides
    fun provideMenuInteractor(mediaRepository: IMenuRepository): MenuInteractor {
        return MenuInteractor(mediaRepository)
    }*/
    @Provides
    fun provideShowChildMenu(mediaRepository: IMenuRepository): ShowChildMenu {
        return ShowChildMenu(mediaRepository)
    }

    @Provides
    fun provideViewModelFactory(useCase: ShowChildMenu): TrackDialogViewModelFactory {
        return TrackDialogViewModelFactory(useCase)
    }

    @Provides
    fun provideMediaDialogAdapter(): TrackDialogAdapter {
        return TrackDialogAdapter()
    }
}