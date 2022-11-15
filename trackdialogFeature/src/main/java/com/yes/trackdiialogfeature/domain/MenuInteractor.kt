package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.CategoryRepository
import com.yes.trackdiialogfeature.data.repository.MediaRepository
import java.util.*
import kotlin.collections.ArrayList

class MenuInteractor(private val categoryRepository: ICategoryRepository, mediaRepository: IMediaRepository) {
    private lateinit var items:LinkedList<MediaItem>
    fun init(){
        categoryRepository.getCategory()
    }
    fun onSelected(position:Int){

    }
    fun getCategories():ArrayList<MediaItem>{
        return categoryRepository.getCategory()
    }
}