package com.yes.core.presentation

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

interface BaseDependency {
    val viewModelFactory: ViewModelProvider.Factory
}
