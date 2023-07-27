package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.repository.dataSource.SettingsDataStore
import com.yes.trackdialogfeature.domain.repository.ISettingsRepository

class SettingsRepositoryImpl(
    private val settingsDataStore: SettingsDataStore
) : ISettingsRepository {
    override fun getCurrentPlayListName(): String {
        return settingsDataStore.getString("currentPlayList")
            ?: run {
                setCurrentPlayListName("Default Playlist")
                return "Default Playlist"
            }
    }

    override fun setCurrentPlayListName(name: String) {
        settingsDataStore.putString("currentPlayList", name)
    }

}