package com.yes.trackdiialogfeature.data.mapper

import com.yes.trackdiialogfeature.data.repository.entity.MenuParam
import com.yes.trackdiialogfeature.domain.Menu

class MenuToParamMapper() {
    fun map(menu: Menu): MenuParam{
       // return MediaParam(menu.items[menu.selected!!].title!!,menu.items[menu.selected!!].title!!)
        return MenuParam(menu.items[menu.selected!!].title!!,Array<String>(1){"'The Soundtrack'"})
    }
}