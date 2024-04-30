package com.yes.playlistfeature.data.repository


import com.yes.core.data.dataSource.SettingsDataSource
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.CURRENT_PLAYLIST_ID
import com.yes.core.data.repository.SettingsRepositoryImpl.PreferencesKeys.CURRENT_TRACK_INDEX
import com.yes.playlistfeature.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsRepositoryImpl(
    private val settingsDataSource: SettingsDataSource
): SettingsRepository {

    override suspend fun subscribeCurrentPlaylistId(): Flow<Long> {
        return settingsDataSource.subscribe(CURRENT_PLAYLIST_ID,1)
        //  dataStore.subscribeCurrentPlaylistId()
    }

    fun subscribeCurrentTrackIndex(): Flow<Int>{
        return settingsDataSource.subscribe(CURRENT_TRACK_INDEX,-1)
       // return dataStore.subscribeTrackIndex().map { it.toInt() }
    }

    suspend fun setCurrentTrackIndex(currentTrackIndex: Int){
        settingsDataSource.set(currentTrackIndex,CURRENT_TRACK_INDEX)
       // dataStore.setCurrentTrackIndex(currentTrackIndex.toLong())
    }

}