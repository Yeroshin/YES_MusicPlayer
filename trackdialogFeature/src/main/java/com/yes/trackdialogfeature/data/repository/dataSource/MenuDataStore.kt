package com.yes.trackdialogfeature.data.repository.dataSource


import com.yes.trackdialogfeature.data.repository.entity.DataException
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity

class MenuDataStore {
    private val menuTree = listOf(
        mapOf(
            "id" to 0,
            "name" to "categories",
            "type" to null,
            "parent" to null
        ),
        mapOf(
            "id" to 1,
            "name" to "artists",
            "type" to null,
            "parent" to 0
        ),
        mapOf(
            "id" to 2,
            "name" to "albums",
            "type" to null,
            "parent" to 0
        ),
        mapOf(
            "id" to 3,
            "name" to "tracks",
            "type" to null,
            "parent" to 0
        ),
        mapOf(
            "id" to 4,
            "name" to null,
            "type" to "artist",
            "parent" to 1
        ),
        mapOf(
            "id" to 5,
            "name" to null,
            "type" to "album",
            "parent" to 2
        ),
        mapOf(
            "id" to 6,
            "name" to null,
            "type" to "track",
            "parent" to 3
        ),
        mapOf(
            "id" to 7,
            "name" to null,
            "type" to "track",
            "parent" to 4
        ),
        mapOf(
            "id" to 8,
            "name" to null,
            "type" to "track",
            "parent" to 5
        )


    )


    fun getItemsWithParentId(value: Int): List<MenuDataStoreEntity> {
        val items = menuTree.filter {
            it["parent"] == value
        }.map {
            MenuDataStoreEntity(
                it["id"] as Int,
                it["name"] as String?,
                it["type"] as String?,
            )
        }

        return items
    }

    fun getItem(id: Int): MenuDataStoreEntity? {

        return menuTree.find {
            it["id"] == id
        }
            ?.let {
                MenuDataStoreEntity(
                    it["id"] as Int,
                    it["name"] as String?,
                    it["type"] as String?
                )
            }
    }

    fun getRootMenuId(): Int {
        return 0
    }
}


