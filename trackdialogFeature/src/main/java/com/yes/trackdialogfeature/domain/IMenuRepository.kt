package com.yes.trackdialogfeature.domain

import com.yes.trackdialogfeature.domain.entity.Menu

interface IMenuRepository {
    fun getChildMenu(id:Int,name:String?): Menu
    fun getRootItems():List<Menu.Item>
}