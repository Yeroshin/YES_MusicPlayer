package com.yes.playlistdialogfeature.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.IdlingRegistry
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.util.EspressoIdlingResource
import com.yes.playlistdialogfeature.data.mapper.Mapper
import com.yes.playlistdialogfeature.data.repository.PlayListDialogRepositoryImpl
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
class TestPlayListDialogModule {
    @Provides
    fun providesPlayListDialogDependency(factory: ViewModelProvider.Factory): PlayListDialog.Dependency {
        return PlayListDialog.Dependency(
            factory
        )
    }

    @Provides
    fun providesCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
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
        playListDialogRepositoryImpl: PlayListDialogRepositoryImpl
    ): SubscribePlayListsUseCase {
        return SubscribePlayListsUseCase(
            dispatcher,
            playListDialogRepositoryImpl
        )
    }
    @Provides
    fun providesAddPlayListUseCase(
        dispatcher: CoroutineDispatcher,
        playListDialogRepositoryImpl: PlayListDialogRepositoryImpl
    ): AddPlayListUseCase {
        return AddPlayListUseCase()
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
    ): SetPlaylistUseCase {
        return SetPlaylistUseCase(

        )
    }
    @Provides
    fun providesUiMapper(): UiMapper {
        return UiMapper()
    }
    @Provides
    fun providesEspressoIdlingResource(): EspressoIdlingResource? {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        return EspressoIdlingResource
    }

    @Provides
    fun providesPlayListDialogViewModelFactory(
        subscribePlayLists: SubscribePlayListsUseCase,
        addPlayListUseCase: AddPlayListUseCase,
        deletePlayListUseCase: DeletePlayListUseCase,
        setPlaylistUseCase: SetPlaylistUseCase,
        uiMapper: UiMapper,
        espressoIdlingResource: EspressoIdlingResource?
    ): ViewModelProvider.Factory {
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