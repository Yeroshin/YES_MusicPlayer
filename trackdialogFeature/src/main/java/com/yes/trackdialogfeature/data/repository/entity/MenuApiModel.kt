package com.yes.trackdialogfeature.data.repository.entity

class MenuApiModel(
    val type: String,
    val name: String?,
    var children:List<MenuApiModel>

)