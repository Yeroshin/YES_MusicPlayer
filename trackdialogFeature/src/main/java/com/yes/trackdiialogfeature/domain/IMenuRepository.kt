package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.entity.MenuParam

interface IMenuRepository {

    fun getMenu(param: MenuParam): Menu


}