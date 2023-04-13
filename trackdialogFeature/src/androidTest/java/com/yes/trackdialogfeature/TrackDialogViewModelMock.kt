package com.yes.trackdialogfeature

import com.yes.core.presentation.BaseViewModel
import com.yes.core.presentation.IBaseViewModel
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class TrackDialogViewModelMock
    : IBaseViewModel<TrackDialogContract.Event,
        TrackDialogContract.State,
        TrackDialogContract.Effect> {
    override val uiState: StateFlow<TrackDialogContract.State>
        get() = TODO("Not yet implemented")
    override val effect: Flow<TrackDialogContract.Effect>
        get() = TODO("Not yet implemented")

    override fun setEvent(event: TrackDialogContract.Event) {
val r=10
        val s =r+10
    }

}