package com.yes.trackdialogfeature.domain

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu.Item

interface IMediaRepository {
    fun getMedia(): DomainResult<Item>
}