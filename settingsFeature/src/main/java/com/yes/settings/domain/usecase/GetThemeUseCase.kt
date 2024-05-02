package com.yes.settings.domain.usecase

import com.yes.core.data.repository.SettingsRepositoryImpl
import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.core.presentation.model.Theme
import kotlinx.coroutines.CoroutineDispatcher

class GetThemeUseCase (
    dispatcher: CoroutineDispatcher,
    private val settingsRepositoryImpl: SettingsRepositoryImpl
) : UseCase<Unit, Theme>(dispatcher) {
    override suspend fun run(params: Unit?): DomainResult<Theme> {

        return DomainResult.Success(settingsRepositoryImpl.getTheme())


    }


}