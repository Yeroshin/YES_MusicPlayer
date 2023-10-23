package com.yes.playlistfeature.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun subscribeCurrentPlaylistId(): Flow<Long>

}