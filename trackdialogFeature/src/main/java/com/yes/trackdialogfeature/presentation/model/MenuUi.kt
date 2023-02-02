package com.yes.trackdialogfeature.presentation.model

import com.yes.core.presentation.UiEvent


data class MenuUi(
    val title: String,
    val items: ArrayList<MediaItem>
) {
    class MediaItem(
        val title: String,
        val name:String,
        var onClick: ((UiEvent) -> Unit)
    ) {
        val iconType:Int=1

    }
}