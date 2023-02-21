package com.yes.trackdialogfeature.domain.entity

class Menu(
    val name: String,
) {
    var type: String = ""

    var parent: Menu? = null
    var children = arrayListOf<Menu>()
}