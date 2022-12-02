package com.yes.trackdiialogfeature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yes.trackdiialogfeature.domain.Menu
import com.yes.trackdiialogfeature.domain.UseCase
import com.yes.trackdiialogfeature.domain.UseCaseWithParam

class TrackDialogViewModelFactory(
    private val useCaseWithParam: UseCaseWithParam<Menu, Menu>,
    private val useCase:UseCase<Menu>
    ) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TrackDialogViewModel(useCaseWithParam,useCase) as T
    }
}