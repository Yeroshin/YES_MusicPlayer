package com.yes.trackdialogfeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel

class MockTrackDialogViewModelFactory(
    private val dep:ViewModel
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return dep as T
    }
}