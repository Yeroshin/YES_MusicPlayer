package com.yes.core.presentation.model

sealed class Theme {
    data object DarkTheme: Theme()
    data object LightTheme: Theme()
}
