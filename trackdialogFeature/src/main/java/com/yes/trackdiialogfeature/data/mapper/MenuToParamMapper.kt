package com.yes.trackdiialogfeature.data.mapper

import com.yes.trackdiialogfeature.data.repository.entity.MediaParam
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.domain.Menu

class MenuToParamMapper() {
    fun map(menu: Menu): MediaParam{
        return MediaParam(menu.items[menu.selected!!].title!!,menu.items[menu.selected!!].title!!)
    }

}