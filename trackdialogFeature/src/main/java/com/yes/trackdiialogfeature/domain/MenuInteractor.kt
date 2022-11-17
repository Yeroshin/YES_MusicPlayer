package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.CategoryRepository
import com.yes.trackdiialogfeature.data.repository.MediaRepository
import java.util.*
import kotlin.collections.ArrayList

class MenuInteractor(private val categoryRepository: ICategoryRepository,private val mediaRepository: IMediaRepository) {
    fun getMenu():Menu{
        return categoryRepository.getCategories()
    }
    fun getMenuContent(menu:Menu):Menu{
        return mediaRepository.getMenu(menu)
    }
}