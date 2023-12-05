package com.yes.alarmclockfeature.di.module

import com.yes.alarmclockfeature.presentation.vm.AlarmClockViewModel
import com.yes.core.presentation.BaseDependency
import dagger.Module
import dagger.Provides

@Module
class AlarmClockModule {
    @Provides
    fun providesAlarmClockViewModelFactory(): AlarmClockViewModel.Factory {
        return AlarmClockViewModel.Factory()
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