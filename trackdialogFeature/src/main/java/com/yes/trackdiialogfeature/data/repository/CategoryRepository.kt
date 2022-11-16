package com.yes.trackdiialogfeature.data.repository

import com.yes.trackdiialogfeature.data.repository.dataSource.CategoryDataStore
import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity
import com.yes.trackdiialogfeature.data.repository.entity.MediaMapper
import com.yes.trackdiialogfeature.domain.ICategoryRepository
import com.yes.trackdiialogfeature.domain.MediaItem

class CategoryRepository(private val categoryDataStore: CategoryDataStore,private val mapper: MediaMapper):ICategoryRepository {
    override fun getCategory(): ArrayList<MediaItem> {
        return mapper.mapToDomain(categoryDataStore.getCategories())
    }
    fun getMenu(){

    }
}