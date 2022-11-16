package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.CategoryRepository
import com.yes.trackdiialogfeature.data.repository.MediaRepository
import java.util.*
import kotlin.collections.ArrayList

class MenuInteractor(private val categoryRepository: ICategoryRepository,private val mediaRepository: IMediaRepository) {
    private lateinit var items:ArrayList<Menu>
    private lateinit var stack:Stack<Menu>
    fun init(){
        categoryRepository.getCategory()
    }
    fun getMenu(){

    }
    fun getCategories():ArrayList<MediaItem>{
        return categoryRepository.getCategory()
    }
}