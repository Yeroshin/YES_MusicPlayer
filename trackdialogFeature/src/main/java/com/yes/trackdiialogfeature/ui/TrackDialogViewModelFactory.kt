package com.yes.trackdiialogfeature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yes.trackdiialogfeature.domain.MenuInteractor

class TrackDialogViewModelFactory(val menuInteractor: MenuInteractor) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TrackDialogViewModel(menuInteractor) as T
    }
}