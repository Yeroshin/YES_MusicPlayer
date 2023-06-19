package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.domain.IMenuRepository
import com.yes.trackdialogfeature.domain.entity.Menu

class MenuRepositoryImpl(
    private val menuMapper:MenuMapper,
    private val menuDataSource: MenuDataStore
):IMenuRepository {


    override fun getChildMenu(id: Int?): Menu {

        id?.let{
            return menuMapper.map(
                menuDataSource.getItem(id)
            )
        }?: run{
            return menuMapper.map(menuDataSource.getItem(0))
        }

    }

    override fun getRootItems(): List<Menu.Item> {
        TODO("Not yet implemented")
    }


}