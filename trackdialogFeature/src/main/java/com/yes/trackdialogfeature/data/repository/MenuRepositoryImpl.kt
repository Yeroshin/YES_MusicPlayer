package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.entity.MenuApiModel
import com.yes.trackdialogfeature.domain.common.Result
import com.yes.trackdialogfeature.domain.entity.Menu

class MenuRepositoryImpl(
    private val menuDataStore: MenuDataStore,
    private val audioDataStore: AudioDataStore,
    private val menuMapper: MenuMapper
) {
/*
    fun getMenu(): MenuApiModel {
        return menuDataStore.getRoot()

    }

    fun getMenu(type: String, name: String?): MenuApiModel {
        val childsType = menuDataStore.getChild(type)
        val childsChildren = audioDataStore.getMediaItems(
            arrayOf(type),
            null,
            null
        )
            .map { item ->
                MenuApiModel(childsType, item, listOf())
            }

        return MenuApiModel(type, null, childsChildren)
    }*/

    fun getMenu(): MenuApiModel {
        val root= menuDataStore.findRoot()
        val children=menuDataStore
        return MenuApiModel()

    }

    fun getMenu(type: String, name: String?): MenuApiModel {
        val childsType = menuDataStore.getChild(type)
        val childsChildren = audioDataStore.getMediaItems(
            arrayOf(type),
            null,
            null
        )
            .map { item ->
                MenuApiModel(childsType, item, listOf())
            }

        return MenuApiModel(type, null, childsChildren)
    }
}