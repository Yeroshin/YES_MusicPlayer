package com.yes.trackdiialogfeature.domain

class Menu(
    val name: String,
    val parent: Menu?
) {
    var selected: Int? = null
    var items: ArrayList<MediaItem>? = null

}