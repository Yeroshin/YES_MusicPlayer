package com.yes.trackdialogfeature.data.mapper

import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity
import com.yes.trackdialogfeature.domain.entity.Menu

class MenuMapper:Mapper<Menu,MenuDataStoreEntity> {
    override fun mapToDomain(repositoryEntity: MenuDataStoreEntity): Menu {
        TODO("Not yet implemented")
    }

    override fun mapToRepository(domainEntity: Menu): MenuDataStoreEntity {
        TODO("Not yet implemented")
    }
}