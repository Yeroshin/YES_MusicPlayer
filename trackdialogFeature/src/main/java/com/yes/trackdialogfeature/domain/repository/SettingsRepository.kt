package com.yes.trackdialogfeature.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun subscribeCurrentPlayListId(): Flow<Long>
    suspend fun setCurrentPlayListId(id:Long)
}
