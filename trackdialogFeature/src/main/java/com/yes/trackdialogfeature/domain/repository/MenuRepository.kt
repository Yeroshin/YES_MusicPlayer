package com.yes.trackdialogfeature.domain.repository

import com.yes.trackdialogfeature.data.repository.entity.DataException
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.MenuException

interface MenuRepository {
    fun getMenu(): DomainResult<Menu>

    fun getMenu(id: Int, name: String): DomainResult<Menu>
}