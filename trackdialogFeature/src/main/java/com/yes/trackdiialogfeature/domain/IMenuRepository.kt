package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.entity.MenuParam

interface IMenuRepository {
    fun getRootMenu():Menu

    fun getMediaItems(menuParam: MenuParam): ArrayList<MediaItem>

}