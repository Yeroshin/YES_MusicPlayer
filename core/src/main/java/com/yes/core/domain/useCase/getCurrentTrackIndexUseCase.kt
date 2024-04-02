package com.yes.core.domain.useCase

import com.yes.core.data.repository.SettingsRepositoryImpl
import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first

class GetCurrentTrackIndexUseCase(
    dispatcher: CoroutineDispatcher,
    private val settingsRepositoryImpl: SettingsRepositoryImpl,
) : UseCase<Any, Int>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Int> {
        return DomainResult.Success(
            settingsRepositoryImpl.subscribeCurrentTrackIndex().first().toInt()
        )
    }

}