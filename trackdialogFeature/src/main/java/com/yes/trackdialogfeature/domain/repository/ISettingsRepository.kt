package com.yes.trackdialogfeature.domain.repository

interface ISettingsRepository {
    fun getCurrentPlayListName():String
    fun setCurrentPlayListName(name: String)
}