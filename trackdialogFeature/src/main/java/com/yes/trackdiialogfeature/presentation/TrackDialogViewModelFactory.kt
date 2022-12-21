package com.yes.trackdiialogfeature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yes.trackdiialogfeature.domain.usecase.GetRootMenu
import com.yes.trackdiialogfeature.domain.usecase.ShowChildMenu
import com.yes.trackdiialogfeature.presentation.mapper.MenuMapper

class TrackDialogViewModelFactory(
    private val showChildMenu: ShowChildMenu,
    private val getRootMenu: GetRootMenu,
    private val menuMapper: MenuMapper
    ) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TrackDialogViewModel(showChildMenu,getRootMenu,menuMapper) as T
    }
}