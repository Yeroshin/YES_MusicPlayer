package com.yes.trackdialogfeature.data.repository


import com.yes.core.repository.dataSource.SettingsSharedPreferences
import com.yes.core.domain.repository.ISettingsRepository

class SettingsRepositoryImpl(
    private val settingsSharedPreferences: SettingsSharedPreferences
) : ISettingsRepository {
    override fun getCurrentPlayListName(): String {
        return settingsSharedPreferences.getString("currentPlayList")?:"Default Playlist"

    }

    override fun setCurrentPlayListName(name: String) {
        settingsSharedPreferences.putString("currentPlayList", name)
    }

}