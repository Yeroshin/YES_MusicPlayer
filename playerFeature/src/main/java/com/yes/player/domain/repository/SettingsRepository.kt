package com.yes.player.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun subscribeCurrentPlaylistId(): Flow<Long>

}