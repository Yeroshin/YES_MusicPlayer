package com.yes.trackdiialogfeature.presentation.entity


data class MenuUi(
    val title: String,
    val type: String,
    val items: ArrayList<MediaItem>
) {
    class MediaItem(
        val title: String
    ) {
        var onClick: (() -> Unit)? = null
    }
}