package com.yes.trackdialogfeature.domain.entity

data class Menu(
    val name: String,
    val children: List<Item>
){
    data class Item(
        val name: String,
        val id: Int,
    )
}