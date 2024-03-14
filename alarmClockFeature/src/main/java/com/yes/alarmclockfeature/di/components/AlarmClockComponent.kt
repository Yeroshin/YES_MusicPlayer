package com.yes.alarmclockfeature.di.components

import android.provider.AlarmClock
import androidx.appcompat.app.AppCompatActivity
import com.yes.alarmclockfeature.di.AlarmClockScope
import com.yes.alarmclockfeature.di.module.AlarmClockModule
import com.yes.alarmclockfeature.domain.usecase.GetCurrentPlaylistTracksUseCase
import com.yes.alarmclockfeature.domain.usecase.SetTracksToPlayerPlaylistUseCase
import com.yes.alarmclockfeature.presentation.ui.AlarmsScreen
import com.yes.core.di.component.BaseComponent
import com.yes.core.di.component.DataComponent
import dagger.Component

@Component(
    dependencies = [DataComponent::class],
    modules = [
        AlarmClockModule::class,
    ]
)
@AlarmClockScope
interface AlarmClockComponent{
    fun getGetCurrentPlaylistTracksUseCase(): GetCurrentPlaylistTracksUseCase
    fun getSetTracksToPlayerPlaylistUseCase(): SetTracksToPlayerPlaylistUseCase
    fun getActivity(): Class<out AppCompatActivity>
    fun getDependency():AlarmsScreen.Dependency
}


