package com.yes.trackdialogfeature.domain.repository

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu

interface IMenuRepository {
    fun getRootMenu():DomainResult<Menu>
    fun getChildMenu():DomainResult<Menu>
    fun getParentMenu():DomainResult<Menu>
}