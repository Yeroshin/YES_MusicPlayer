package com.yes.trackdialogfeature.domain.repository

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase.Params

interface IMenuRepository {
    fun getRootMenu():DomainResult<Menu>
    fun getChildMenu(id:Int,name:String):DomainResult<Menu>
    fun getParentMenu():DomainResult<Menu>
}