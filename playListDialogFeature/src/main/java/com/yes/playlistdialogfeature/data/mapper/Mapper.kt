package com.yes.playlistdialogfeature.data.mapper

import com.yes.core.repository.entity.PlayListDataBaseEntity
import com.yes.playlistdialogfeature.domain.entity.Item

class Mapper {
    fun map(playlist: PlayListDataBaseEntity):Item{
        return Item(
            id=playlist.id?:0,
            name=playlist.name,
        )
    }
    fun map(playlist:Item): PlayListDataBaseEntity {
        return PlayListDataBaseEntity(
            id=playlist.id,
            name=playlist.name,
        )
    }
    fun map(playlistName:String): PlayListDataBaseEntity {
        return PlayListDataBaseEntity(
            null,
            name=playlistName,
        )
    }

}