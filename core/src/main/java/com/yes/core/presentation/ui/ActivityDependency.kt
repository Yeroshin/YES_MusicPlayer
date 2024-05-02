package com.yes.core.presentation.ui

import androidx.lifecycle.ViewModelProvider
import com.yes.core.data.repository.SettingsRepositoryImpl

class ActivityDependency (
    val viewModelFactory: ViewModelProvider.Factory,
    val settingsRepositoryImpl: SettingsRepositoryImpl
)