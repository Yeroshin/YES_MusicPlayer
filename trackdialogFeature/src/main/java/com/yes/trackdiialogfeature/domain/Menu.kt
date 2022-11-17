package com.yes.trackdiialogfeature.domain

class Menu (
    val name:String,
    var selected:Int?,
    val parent:Menu?,
    var items:ArrayList<MediaItem>
)