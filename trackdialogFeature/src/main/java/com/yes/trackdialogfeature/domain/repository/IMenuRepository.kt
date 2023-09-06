package com.yes.trackdialogfeature.domain.repository

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item

interface IMenuRepository {
    fun getRootMenu():Menu?
    fun getChildMenu(id:Int): Menu?
    fun getItem(id:Int):Item?
    fun getChildItem(id: Int): Item?
}