package com.yes.core.domain.useCase

import com.yes.core.data.repository.SettingsRepositoryImpl
import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.CoroutineDispatcher

class SetSettingsTrackIndexUseCase (
    dispatcher: CoroutineDispatcher,
    private val settingsRepositoryImpl: SettingsRepositoryImpl,
) : UseCase<SetSettingsTrackIndexUseCase.Params, Int>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Int> {
        return params?.let {
            settingsRepositoryImpl.setCurrentTrackIndex(params.index)
            DomainResult.Success(1)
        }?:DomainResult.Error(DomainResult.UnknownException)

    }
    data class Params(
        val index:Int
    )
}