package com.yes.alarmclockfeature.di.module

import android.content.Context
import com.yes.alarmclockfeature.data.dataSource.AlarmDataSource
import com.yes.alarmclockfeature.data.mapper.Mapper
import com.yes.alarmclockfeature.data.repository.AlarmListRepository
import com.yes.alarmclockfeature.data.repository.AlarmManagerRepository
import com.yes.alarmclockfeature.data.repository.PlayListRepositoryImpl
import com.yes.alarmclockfeature.data.repository.PlayerRepository
import com.yes.alarmclockfeature.data.repository.SettingsRepositoryImpl
import com.yes.alarmclockfeature.domain.usecase.AddAlarmUseCase
import com.yes.alarmclockfeature.domain.usecase.DeleteAlarmUseCase
import com.yes.alarmclockfeature.domain.usecase.GetCurrentPlaylistTracksUseCase
import com.yes.alarmclockfeature.domain.usecase.SetAlarmUseCase
import com.yes.alarmclockfeature.domain.usecase.SetTracksToPlayerPlaylistUseCase
import com.yes.alarmclockfeature.domain.usecase.SubscribeAlarmsUseCase
import com.yes.alarmclockfeature.presentation.mapper.MapperUI
import com.yes.alarmclockfeature.presentation.vm.AlarmClockViewModel
import com.yes.core.data.dataSource.PlayerDataSource
import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.core.di.module.IoDispatcher
import com.yes.core.di.module.MainDispatcher
import com.yes.core.domain.repository.IAlarmDao
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.presentation.BaseDependency
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class AlarmClockModule {
    @Provides
    fun providesPlayerRepository(
        mapper:Mapper,
        playerDataSource: PlayerDataSource
    ): PlayerRepository {
        return PlayerRepository(
            mapper,
            playerDataSource
        )
    }
    @Provides
    fun providesSetTracksToPlayerPlaylistUseCase(
        @MainDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository,
    ): SetTracksToPlayerPlaylistUseCase {
        return SetTracksToPlayerPlaylistUseCase(
            dispatcher,
            playerRepository
        )
    }

    @Provides
    fun providesSettingsRepository(
        dataStore: SettingsDataStore
    ): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(
            dataStore
        )
    }
    @Provides
    fun providesPlayListRepository(
        mapper: Mapper,
        playListDao: IPlayListDao,
    ): PlayListRepositoryImpl {
        return PlayListRepositoryImpl(
            mapper,
            playListDao,
        )
    }
    @Provides
    fun providesGetCurrentPlaylistTracksUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        playListRepositoryImpl: PlayListRepositoryImpl,
        settingsRepository: SettingsRepositoryImpl
    ): GetCurrentPlaylistTracksUseCase {
        return GetCurrentPlaylistTracksUseCase(
            dispatcher,
            playListRepositoryImpl,
            settingsRepository
        )
    }

    @Provides
    fun providesMapperUI(): MapperUI {
        return MapperUI()
    }

    @Provides
    fun providesMapper(): Mapper {
        return Mapper()
    }

    @Provides
    fun providesAlarmListRepository(
        mapper: Mapper,
        alarmDao: IAlarmDao
    ): AlarmListRepository {
        return AlarmListRepository(
            mapper,
            alarmDao
        )
    }

    @Provides
    fun providesAddAlarmUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        alarmListRepository: AlarmListRepository,
        alarmManagerRepository: AlarmManagerRepository
    ): AddAlarmUseCase {
        return AddAlarmUseCase(
            dispatcher,
            alarmListRepository,
            alarmManagerRepository
        )
    }

    @Provides
    fun providesSubscribeAlarmsUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        alarmListRepository: AlarmListRepository
    ): SubscribeAlarmsUseCase {
        return SubscribeAlarmsUseCase(
            dispatcher,
            alarmListRepository
        )
    }

    @Provides
    fun providesDeleteAlarmUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        alarmListRepository: AlarmListRepository
    ): DeleteAlarmUseCase {
        return DeleteAlarmUseCase(
            dispatcher,
            alarmListRepository
        )
    }

    @Provides
    fun providesAlarmDataSource(
        context: Context
    ): AlarmDataSource {
        return AlarmDataSource(
            context
        )
    }

    @Provides
    fun providesAlarmManagerRepository(
        alarmDataSource: AlarmDataSource
    ): AlarmManagerRepository {
        return AlarmManagerRepository(
            alarmDataSource
        )
    }

    @Provides
    fun providesSetAlarmUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        alarmListRepository: AlarmListRepository,
        alarmManagerRepository: AlarmManagerRepository
    ): SetAlarmUseCase {
        return SetAlarmUseCase(
            dispatcher,
            alarmListRepository,
            alarmManagerRepository,
        )
    }

    @Provides
    fun providesAlarmClockViewModelFactory(
        mapper: MapperUI,
        addAlarmUseCase: AddAlarmUseCase,
        subscribeAlarmsUseCase: SubscribeAlarmsUseCase,
        deleteAlarmUseCase: DeleteAlarmUseCase,
        setAlarmUseCase: SetAlarmUseCase
    ): AlarmClockViewModel.Factory {
        return AlarmClockViewModel.Factory(
            mapper,
            addAlarmUseCase,
            subscribeAlarmsUseCase,
            deleteAlarmUseCase,
            setAlarmUseCase
        )
    }

    @Provides
    fun providesDependency(
        factory: AlarmClockViewModel.Factory,
    ): BaseDependency {
        return BaseDependency(
            factory
        )
    }
}