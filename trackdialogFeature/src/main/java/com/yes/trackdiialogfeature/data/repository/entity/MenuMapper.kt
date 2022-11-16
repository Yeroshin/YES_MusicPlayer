package com.yes.trackdiialogfeature.data.repository.entity

import com.yes.trackdiialogfeature.data.mapper.Mapper
import com.yes.trackdiialogfeature.domain.Menu

class MenuMapper:Mapper<Menu,MediaEntity> {
    override fun mapToDomain(repositoryEntity: ArrayList<MediaEntity>): Menu {
        TODO("Not yet implemented")
    }

    override fun matToRepository(domainEntity: Menu): ArrayList<MediaEntity> {
        TODO("Not yet implemented")
    }
}