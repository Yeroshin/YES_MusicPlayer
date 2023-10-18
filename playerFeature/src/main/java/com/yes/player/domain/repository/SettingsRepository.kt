package com.yes.player.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun subscribeCurrentPlaylistId(): Flow<Long>

}