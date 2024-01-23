package com.yes.core.di.component

import com.yes.core.presentation.BaseDependency
import com.yes.core.presentation.BaseFragment
@Deprecated("do not use define separate Dependency in every screen",)
interface BaseComponent{
    fun getDependency(): BaseDependency
}