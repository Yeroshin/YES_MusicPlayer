package com.yes.trackdiialogfeature.domain.entity

class Menu(
    val name: String
) {
    var title:String?=null
    var type:String?=null
    var parent: Menu?=null
    var children= arrayListOf<Menu>()
    var selected: Int = -1

}