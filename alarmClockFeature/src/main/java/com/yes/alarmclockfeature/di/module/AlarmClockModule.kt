package com.yes.alarmclockfeature.di.module

import android.app.AlarmManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
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
import com.yes.alarmclockfeature.domain.usecase.SetNearestAlarmUseCase
import com.yes.alarmclockfeature.domain.usecase.SetAndPlayTracksToPlayerPlaylistUseCase
import com.yes.alarmclockfeature.domain.usecase.SubscribeAlarmsUseCase
import com.yes.alarmclockfeature.presentation.mapper.MapperUI
import com.yes.alarmclockfeature.presentation.ui.AlarmsScreen
import com.yes.alarmclockfeature.presentation.vm.AlarmClockViewModel
import com.yes.alarmclockfeature.util.CalendarFactory
import com.yes.core.data.dataSource.PlayerDataSource
import com.yes.core.data.dataSource.SettingsDataSource
import com.yes.core.di.module.IoDispatcher
import com.yes.core.di.module.MainDispatcher
import com.yes.core.domain.repository.IAlarmDao
import com.yes.core.domain.repository.IPlayListDao
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import java.util.Calendar

@Module
class AlarmClockModule(
    private val activityClass: Class<out AppCompatActivity>
) {
    @Provides
    fun providesActivityClass(): Class<out AppCompatActivity> {
        return activityClass
    }
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
    fun providesSetAndPlayTracksToPlayerPlaylistUseCase(
        @MainDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository,
        settingsRepository: SettingsRepositoryImpl
    ): SetAndPlayTracksToPlayerPlaylistUseCase {
        return SetAndPlayTracksToPlayerPlaylistUseCase(
            dispatcher,
            playerRepository,
            settingsRepository
        )
    }

    @Provides
    fun providesSettingsRepository(
        settingsDataSource: SettingsDataSource
    ): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(
            settingsDataSource
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
    fun providesMapperUI(
        calendarFactory:CalendarFactory
    ): MapperUI {
        return MapperUI(
            calendarFactory
        )
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
    fun providesCalendarFactory(
    ): CalendarFactory {
        return CalendarFactory()
    }
    @Provides
    fun providesSetNextAlarmUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        alarmListRepository: AlarmListRepository,
        alarmManagerRepository: AlarmManagerRepository,
        calendarFactory:CalendarFactory
    ): SetNearestAlarmUseCase {
        return SetNearestAlarmUseCase(
            dispatcher,
            alarmListRepository,
            alarmManagerRepository,
            calendarFactory
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
    fun providesAlarmManager(
        context: Context,
    ): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }
    @Provides
    fun providesAlarmDataSource(
        context: Context,
        alarmManager: AlarmManager
    ): AlarmDataSource {
        return AlarmDataSource(
            context,
            Calendar.getInstance(),
            alarmManager
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
        setAlarmUseCase: SetAlarmUseCase,
        setNearestAlarmUseCase:SetNearestAlarmUseCase
    ): AlarmClockViewModel.Factory {
        return AlarmClockViewModel.Factory(
            mapper,
            addAlarmUseCase,
            subscribeAlarmsUseCase,
            deleteAlarmUseCase,
            setAlarmUseCase,
            setNearestAlarmUseCase
        )
    }

    @Provides
    fun providesDependency(
        factory: AlarmClockViewModel.Factory,
    ): AlarmsScreen.Dependency {
        return AlarmsScreen.Dependency(
            factory
        )
    }
}