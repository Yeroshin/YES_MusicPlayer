package com.yes.trackdialogfeature.data.repository.entity

/*class MenuApiModel(
    val type: String,
    val name: String?,
    var children:Array<MenuApiModel>

)*/
data class MenuDataStoreEntityOld(
    val id:Int,
    val name:String?,
    val type:String?,
    val parent:Int?
)