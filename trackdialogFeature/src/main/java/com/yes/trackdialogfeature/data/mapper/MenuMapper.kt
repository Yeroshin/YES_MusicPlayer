package com.yes.trackdialogfeature.data.mapper

import com.yes.trackdialogfeature.data.repository.entity.MenuApiModel
import com.yes.trackdialogfeature.domain.entity.Menu

class MenuMapper:Mapper<Menu,MenuApiModel> {
    override fun mapToDomain(repositoryEntity: MenuApiModel): Menu {
        val menu=Menu(
            repositoryEntity.type
        )
        menu.type=repositoryEntity.type
       // repositoryEntity.children.forEach()
       // menu.children=repositoryEntity.children
        return  menu
    }

    override fun mapToRepository(domainEntity: Menu): MenuApiModel {
        TODO("Not yet implemented")
    }
}