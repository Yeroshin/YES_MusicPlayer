package com.yes.playlistdialogfeature.data.mapper

import com.yes.core.domain.models.PlayList
import com.yes.playlistdialogfeature.domain.entity.Item

class Mapper {
    fun map(playlist:PlayList):Item{
        return Item(
            playlist.name,
        )
    }
}