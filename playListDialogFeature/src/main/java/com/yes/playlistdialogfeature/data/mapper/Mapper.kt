package com.yes.playlistdialogfeature.data.mapper

import com.yes.core.domain.models.PlayList
import com.yes.playlistdialogfeature.domain.entity.Item

class Mapper {
    fun map(playlist:PlayList):Item{
        return Item(
            id=playlist.id?:0,
            name=playlist.name,
        )
    }
    fun map(playlistName:String):PlayList{
        return PlayList(
            null,
            name=playlistName,
        )
    }

}