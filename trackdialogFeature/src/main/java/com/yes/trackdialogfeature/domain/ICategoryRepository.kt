package com.yes.trackdialogfeature.domain

import com.yes.trackdialogfeature.domain.entity.Menu

interface ICategoryRepository {
    fun getCategories(): Menu
}