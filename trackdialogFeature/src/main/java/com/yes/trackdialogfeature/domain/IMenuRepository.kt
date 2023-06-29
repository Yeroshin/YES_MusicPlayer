package com.yes.trackdialogfeature.domain

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu

interface IMenuRepository {
    fun getChildMenu(id:Int,name:String): DomainResult<Menu>
    fun getRootItems():DomainResult<List<Menu.Item>>
}