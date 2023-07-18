package com.yes.trackdialogfeature.domain.repository

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase.Params

interface IMenuRepository {
    fun getRootMenu():Menu?
    fun getChildMenu(id:Int):Menu?
    fun getChildItem(id:Int): Item?
}