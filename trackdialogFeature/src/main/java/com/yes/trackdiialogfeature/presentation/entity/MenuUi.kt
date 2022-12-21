package com.yes.trackdiialogfeature.presentation.entity


data class MenuUi(
    val title: String,
    val items: ArrayList<MediaItem>
) {
    class MediaItem(
        val title: String,
        val name:String,
        var onClick: ((String,String) -> Unit)
    ) {
        val iconType:Int=1

    }
}