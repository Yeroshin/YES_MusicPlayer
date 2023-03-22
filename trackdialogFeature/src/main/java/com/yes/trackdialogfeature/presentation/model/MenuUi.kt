package com.yes.trackdialogfeature.presentation.model

import com.yes.core.presentation.UiEvent
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract


data class MenuUi(
    val title: String,
    val items: ArrayList<MediaItem>
) {
    class MediaItem(
        val id:Int,
        val name:String,
        var onClick: ((TrackDialogContract.Event) -> Unit)
    ) {
        val iconType:Int=1

    }
}