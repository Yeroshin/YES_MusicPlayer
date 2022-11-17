package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.entity.MediaParam

interface IMediaRepository {
    fun getMenu(menu:Menu):Menu

}