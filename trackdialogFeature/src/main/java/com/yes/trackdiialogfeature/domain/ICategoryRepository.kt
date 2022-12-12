package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.domain.entity.Menu

interface ICategoryRepository {
    fun getCategories(): Menu
}