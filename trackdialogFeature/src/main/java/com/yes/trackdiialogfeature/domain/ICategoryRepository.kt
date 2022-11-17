package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.entity.MediaEntity

interface ICategoryRepository {
    fun getCategories():Menu
}