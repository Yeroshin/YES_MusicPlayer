package com.yes.playlistdialogfeature.data.mapper

import com.yes.core.repository.entity.PlayListEntity
import com.yes.playlistdialogfeature.domain.entity.Item

class Mapper {
    fun map(playlist: PlayListEntity):Item{
        return Item(
            id=playlist.id?:0,
            name=playlist.name,
        )
    }
    fun map(playlist:Item): PlayListEntity {
        return PlayListEntity(
            id=playlist.id,
            name=playlist.name,
        )
    }
    fun map(playlistName:String): PlayListEntity {
        return PlayListEntity(
            null,
            name=playlistName,
        )
    }

}