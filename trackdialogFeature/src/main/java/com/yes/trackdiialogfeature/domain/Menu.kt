package com.yes.trackdiialogfeature.domain

import android.view.ViewParent

class Menu(
    val name: String,
    val type:String?
) {
    var parent: Menu?=null
    var selected: Int = -1
    var children: ArrayList<Menu> = arrayListOf()
    var items:ArrayList<MediaItem> = arrayListOf()
}