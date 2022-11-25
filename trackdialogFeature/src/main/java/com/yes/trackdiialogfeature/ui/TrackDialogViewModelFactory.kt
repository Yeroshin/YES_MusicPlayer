package com.yes.trackdiialogfeature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yes.trackdiialogfeature.domain.Menu
import com.yes.trackdiialogfeature.domain.MenuInteractor
import com.yes.trackdiialogfeature.domain.UseCase

class TrackDialogViewModelFactory(val usecase: UseCase<Menu, Menu>) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TrackDialogViewModel(usecase) as T
    }
}