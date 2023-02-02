package com.yes.trackdialogfeature.domain.entity

class Menu(
    val name: String,
) {
    var title:String=""

    var parent: Menu?=null
    var children= arrayListOf<Menu>()
}