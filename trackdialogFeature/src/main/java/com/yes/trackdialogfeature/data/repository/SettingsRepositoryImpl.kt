package com.yes.trackdialogfeature.data.repository


import com.yes.core.repository.dataSource.SettingsDataStore
import com.yes.core.domain.repository.ISettingsRepository

class SettingsRepositoryImpl(
    private val settingsDataStore: SettingsDataStore
) : ISettingsRepository {
    override fun getCurrentPlayListName(): String {
        return settingsDataStore.getString("currentPlayList")?:"Default Playlist"

    }

    override fun setCurrentPlayListName(name: String) {
        settingsDataStore.putString("currentPlayList", name)
    }

}