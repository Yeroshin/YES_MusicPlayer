package com.yes.core.di.component

import com.yes.core.presentation.BaseDependency
@Deprecated("do not use define separate Dependency in every screen",)
interface BaseComponent{
    fun getDependency(): BaseDependency
}