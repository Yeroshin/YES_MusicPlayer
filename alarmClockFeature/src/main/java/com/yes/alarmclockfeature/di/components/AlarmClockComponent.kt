package com.yes.alarmclockfeature.di.components

import com.yes.alarmclockfeature.di.AlarmClockScope
import com.yes.alarmclockfeature.di.module.AlarmClockModule
import com.yes.core.di.component.AudioSessionIdComponent
import com.yes.core.di.component.BaseComponent
import com.yes.core.di.component.CoreComponent
import com.yes.core.presentation.BaseDependency
import com.yes.core.presentation.BaseFragment
import dagger.Component

@Component(
    dependencies = [CoreComponent::class],
    modules = [
        AlarmClockModule::class,
    ]
)
@AlarmClockScope
interface AlarmClockComponent:BaseComponent


