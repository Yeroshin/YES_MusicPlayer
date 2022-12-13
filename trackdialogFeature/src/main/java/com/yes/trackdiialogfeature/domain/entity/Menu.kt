package com.yes.trackdiialogfeature.domain.entity

class Menu(
    val name: String,
    var type:String
) {
    var title:String=""

    var parent: Menu?=null
    var children= arrayListOf<Menu>()
    var selected: Int = -1

}