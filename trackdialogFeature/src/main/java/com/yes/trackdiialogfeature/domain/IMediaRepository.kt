package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.entity.MediaParam

interface IMediaRepository {
    fun getMedia(param:MediaParam)
}