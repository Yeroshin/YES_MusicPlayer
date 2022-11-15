package com.yes.trackdiialogfeature.di.module

import android.app.Activity
import com.yes.trackdiialogfeature.data.mapper.Mapper
import com.yes.trackdiialogfeature.data.repository.CategoryRepository
import com.yes.trackdiialogfeature.data.repository.MediaRepository
import com.yes.trackdiialogfeature.data.repository.dataSource.CategoryDataStore
import com.yes.trackdiialogfeature.data.repository.dataSource.MediaDataStore
import com.yes.trackdiialogfeature.data.repository.entity.MediaMapper
import com.yes.trackdiialogfeature.domain.ICategoryRepository
import com.yes.trackdiialogfeature.domain.IMediaRepository
import com.yes.trackdiialogfeature.domain.MenuInteractor
import com.yes.trackdiialogfeature.presentation.TrackDialogAdapter
import com.yes.trackdiialogfeature.presentation.TrackDialogViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class TrackDialogModule(val context: Activity) {
    @Provides
    fun provideContext():Activity{
        return context
    }
    @Provides
    fun providesCategoryDataStore():CategoryDataStore{
        return CategoryDataStore(context)
    }
    @Provides
    fun provideMediaMapper(): MediaMapper {
        return MediaMapper()
    }
    @Provides
    fun provideCategoryRepository(categoryDataStore: CategoryDataStore,mapper: MediaMapper):ICategoryRepository{
        return CategoryRepository(categoryDataStore,mapper)
    }
    @Provides
    fun providesMediaDataStore():MediaDataStore{
        return MediaDataStore(context)
    }
    @Provides
    fun provideMediaRepository(mediaDataStore: MediaDataStore):IMediaRepository{
        return MediaRepository(mediaDataStore)
    }
    @Provides
    fun provideMenuInteractor(categoryRepository: ICategoryRepository, mediaRepository: IMediaRepository):MenuInteractor{
        return MenuInteractor(categoryRepository, mediaRepository)
    }
    @Provides
    fun provideViewModelFactory(menuInteractor: MenuInteractor):TrackDialogViewModelFactory{
        return TrackDialogViewModelFactory(menuInteractor)
    }
    @Provides
    fun provideMediaDialogAdapter():TrackDialogAdapter{
        return TrackDialogAdapter()
    }
}