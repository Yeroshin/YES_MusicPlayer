package com.yes.playlistdialogfeature.domain.entity

data class Item (
    val id: Long,
    val name:String,
    var current:Boolean=false
)