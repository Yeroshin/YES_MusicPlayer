package com.yes.trackdialogfeature.domain

import com.yes.trackdialogfeature.domain.entity.Menu

interface IMenuRepository {
    fun getChildMenu(id:Int?): Menu
    fun getRootItems():List<Menu.Item>
}