package com.yes.settings.domain.usecase

import com.yes.core.data.repository.SettingsRepositoryImpl
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.core.presentation.model.Theme
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class SetThemeUseCase (
    dispatcher: CoroutineDispatcher,
    private val settingsRepositoryImpl: SettingsRepositoryImpl
) : UseCase<SetThemeUseCase.Params, Boolean>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Boolean> {

        return params?.let {
            settingsRepositoryImpl.setTheme(it.theme)
            DomainResult.Success(true)
        } ?: DomainResult.Error(DomainResult.UnknownException)

    }

    data class Params(
        val theme:Theme
    )
}