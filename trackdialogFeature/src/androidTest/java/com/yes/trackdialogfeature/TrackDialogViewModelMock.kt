package com.yes.trackdialogfeature

import com.yes.core.presentation.BaseViewModel
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract

class TrackDialogViewModelMock: BaseViewModel<TrackDialogContract.Event, TrackDialogContract.State, TrackDialogContract.Effect>() {
    override fun createInitialState(): TrackDialogContract.State {
        return TrackDialogContract.State(
            TrackDialogContract.TrackDialogState.Idle
        )
    }

    override fun handleEvent(event: TrackDialogContract.Event) {

    }
}