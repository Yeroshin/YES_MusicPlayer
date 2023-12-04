package com.yes.core.di.component

import com.yes.core.presentation.BaseDependency
import com.yes.core.presentation.BaseFragment

interface BaseComponent{
    fun getDependency(): BaseDependency
}