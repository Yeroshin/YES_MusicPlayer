package com.yes.trackdialogfeature.domain.repository

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu

interface MenuRepository {
    fun getMenu(): DomainResult<Menu>

    fun getMenu(id: Int, name: String): DomainResult<Menu>
    fun getMenu(params: Params)
    data class Params(val id: Int)
}