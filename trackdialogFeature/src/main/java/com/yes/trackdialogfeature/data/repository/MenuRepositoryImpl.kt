package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.domain.common.Result
import com.yes.trackdialogfeature.domain.entity.Menu

class MenuRepositoryImpl(
    private val menuDataStore: MenuDataStore,
    private val audioDataStore: AudioDataStore,
    private val menuMapper: MenuMapper
) {

    fun getMenu(): Result<Menu> {
        return Result.Success(
            menuMapper.mapToDomain(
                menuDataStore.getRoot()
            )
        )
    }

    fun getMenu(type: String, name: String?): Result<Menu> {


        return Result.Success(Menu(""))
    }
}