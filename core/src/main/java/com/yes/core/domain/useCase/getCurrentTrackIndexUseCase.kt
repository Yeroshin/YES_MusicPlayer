package com.yes.core.domain.useCase

import com.yes.core.data.repository.SettingsRepositoryImpl
import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GetCurrentTrackIndexUseCase(
    dispatcher: CoroutineDispatcher,
    private val settingsRepositoryImpl: SettingsRepositoryImpl,
) : UseCase<Any, Flow<Int>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Flow<Int>> {
        return DomainResult.Success(
            settingsRepositoryImpl.subscribeCurrentTrackIndex().map { it.toInt() }
        )
    }

}