package com.yes.playlistdialogfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistdialogfeature.data.repository.SettingsRepositoryImpl
import com.yes.playlistdialogfeature.domain.entity.Item
import kotlinx.coroutines.CoroutineDispatcher

class SetPlaylistUseCase(
    dispatcher: CoroutineDispatcher,
    private val settingsRepository: SettingsRepositoryImpl
) : UseCase<SetPlaylistUseCase.Params, Long>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Long> {
        val item=params?.items?.find { it.current }
        settingsRepository.updateCurrentPlaylistId(
            item?.id?:0
        )
        return DomainResult.Success(0)
    }
    data class Params(val items: List<Item>)

}