package com.yes.trackdiialogfeature.domain

class Menu(
    val name: String,
    val parent: Menu?
) {
    var selected: Int = -1
    var items: ArrayList<MediaItem> = arrayListOf()

}