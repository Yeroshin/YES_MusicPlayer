package com.yes.trackdialogfeature.data.repository


import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.trackdialogfeature.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val settings: SettingsDataStore
) : SettingsRepository {
    override suspend fun subscribeCurrentPlayListId(): Flow<Long> {
        return settings.subscribeCurrentPlaylistId()

    }

    override suspend fun setCurrentPlayListId(id:Long) {
        settings.setCurrentPlaylistId(id)
    }

}