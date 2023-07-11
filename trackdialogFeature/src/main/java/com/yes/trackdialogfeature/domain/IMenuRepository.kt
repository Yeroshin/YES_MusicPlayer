package com.yes.trackdialogfeature.domain

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item

interface IMenuRepository {
    fun getChildMenu(id:Int,name:String): DomainResult<Menu>
    fun getRootMenu():DomainResult<Menu>
    fun saveToPlayList(playlistName:String,menu: List<Item>):DomainResult<Boolean>
}