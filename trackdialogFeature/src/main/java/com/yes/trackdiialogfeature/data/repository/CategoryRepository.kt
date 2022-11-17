package com.yes.trackdiialogfeature.data.repository

import com.yes.trackdiialogfeature.data.repository.dataSource.CategoryDataStore
import com.yes.trackdiialogfeature.data.mapper.MediaMapper
import com.yes.trackdiialogfeature.domain.ICategoryRepository
import com.yes.trackdiialogfeature.domain.Menu

class CategoryRepository(private val categoryDataStore: CategoryDataStore,private val mapper: MediaMapper):ICategoryRepository {
    override fun getCategories(): Menu {
        return Menu(categoryDataStore.getMenuNameForCategories(),0,null,mapper.mapToDomain(categoryDataStore.getCategories()))
    }
}