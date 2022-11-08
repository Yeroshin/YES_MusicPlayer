package com.yes.trackdiialogfeature.data.repository

import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity
import com.yes.trackdiialogfeature.domain.ICategoryRepository

class CategoryRepository(private val repository: ICategoryRepository):ICategoryRepository {
    override fun getCategory(): Array<MediaEntity> {
        return repository.getCategory()
    }
}