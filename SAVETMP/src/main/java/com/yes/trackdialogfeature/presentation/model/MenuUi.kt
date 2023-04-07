package com.yes.trackdialogfeature.presentation.model

import com.yes.core.presentation.UiEvent
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract


data class MenuUi(
    val title: String,
    val items: List<MediaItem>
) {
    data class MediaItem(
        val id:Int,
        val name:String,
        val iconType:Int=1,
        val onClick: ((TrackDialogContract.Event) -> Unit)

    )

}