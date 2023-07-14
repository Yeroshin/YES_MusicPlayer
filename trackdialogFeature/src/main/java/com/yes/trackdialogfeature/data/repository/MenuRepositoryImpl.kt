package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.mapper.MenuRepositoryMapper
import com.yes.trackdialogfeature.data.repository.dataSource.MediaDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.entity.PlayListDao
import com.yes.trackdialogfeature.domain.IMenuRepository
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item

class MenuRepositoryImpl(
    private val menuRepositoryMapper: MenuRepositoryMapper,
    private val menuDataStore: MenuDataStore,
    private val mediaDataStore: MediaDataStore,
    private val playListDao: PlayListDao
) : IMenuRepository {


    override fun getChildMenu(id: Int, name: String): DomainResult<Menu> {


        val currentMenu = menuDataStore.getItem(id)
        val childMenu = menuDataStore.getItemsWithParentId(id).last()


        val selection: String? = currentMenu.type
        val selectionArgs = selection?.let {
            arrayOf(name)
        } ?: run {
            emptyArray()
        }
        val projection = childMenu.type?.let { arrayOf(it) } ?: arrayOf()
        val items = mediaDataStore.getMediaItems(
            projection,
            selection,
            selectionArgs,
        ).map { menuRepositoryMapper.mapToItem(it) }
        val menu = menuRepositoryMapper.map(childMenu)
        menu.children.toMutableList().addAll(items)
        return DomainResult.Success(menu)
    }

    override fun getRootMenu(): DomainResult<Menu> {
        val menu = menuRepositoryMapper.map(menuDataStore.getItem(0))
        val items = menuDataStore.getItemsWithParentId(0)
            .map { menuRepositoryMapper.mapToItem(it) }
        menu.children.toMutableList().addAll(items)
        return DomainResult.Success(menu)
    }

    override fun saveToPlayList(playlistName: String, items: List<Item>): DomainResult<Boolean> {

        /* val audioItems = items.flatMap { raw ->
             audioDataStore.getAudioItems(
                 menuDataStore.getItem(raw.id).type,
                 arrayOf(raw.name)
             ).map {
                 menuRepositoryMapper.mapToTrackEntity(
                     it
                 )
             }
         }*/
        playListDao.saveTracks(
            items.flatMap { item ->
                mediaDataStore.getAudioItems(
                    menuDataStore.getItem(item.id).type,
                    arrayOf(item.name)
                ).map {
                    menuRepositoryMapper.mapToTrackEntity(
                        it
                    )
                }.map {
                    it.copy(playlistName = playlistName)
                }
            }
        )
        return DomainResult.Success(true)
    }


}