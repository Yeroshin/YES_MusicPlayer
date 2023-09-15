package com.yes.core.domain.repository

interface ISettingsRepository {
    fun getCurrentPlayListName():String
    fun setCurrentPlayListName(name: String)
}