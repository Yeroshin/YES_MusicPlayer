package com.yes.alarmclockfeature.di.module

import com.yes.alarmclockfeature.data.mapper.Mapper
import com.yes.alarmclockfeature.data.repository.AlarmListRepository
import com.yes.alarmclockfeature.domain.usecase.AddAlarmUseCase
import com.yes.alarmclockfeature.presentation.mapper.MapperUI
import com.yes.alarmclockfeature.presentation.vm.AlarmClockViewModel
import com.yes.core.di.module.IoDispatcher
import com.yes.core.domain.repository.IAlarmDao
import com.yes.core.presentation.BaseDependency
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class AlarmClockModule {
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
        alarmListRepository: AlarmListRepository
    ): AddAlarmUseCase {
        return AddAlarmUseCase(
            dispatcher,
            alarmListRepository
        )
    }

    @Provides
    fun providesAlarmClockViewModelFactory(
        mapper: MapperUI,
        addAlarmUseCase: AddAlarmUseCase
    ): AlarmClockViewModel.Factory {
        return AlarmClockViewModel.Factory(
            mapper,
            addAlarmUseCase
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