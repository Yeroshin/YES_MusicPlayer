package com.yes.trackdiialogfeature.presentation.entity


data class MenuUi(
    val title: String,
    val items: ArrayList<MediaItem>
) {
    class MediaItem(
        val title: String,
        val name:String
    ) {
        val iconType:Int=1
        var onClick: (() -> Unit)? = null
    }
}