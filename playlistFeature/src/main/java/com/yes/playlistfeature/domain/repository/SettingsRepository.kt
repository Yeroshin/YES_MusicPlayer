package com.yes.playlistfeature.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun subscribeCurrentPlaylistId(): Flow<Long>

}