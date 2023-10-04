package com.yes.trackdialogfeature.domain.entity

data class Menu(
    val name: String,
    val children: List<Item>
) {
    data class Item(
        val id: Int,
        val name: String,
        val type:String?,
        val selected:Boolean
        )
}