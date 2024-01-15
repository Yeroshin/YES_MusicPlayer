package com.yes.trackdialogfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.data.repository.NetworkRepository
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.MenuException
import com.yes.trackdialogfeature.domain.repository.IMenuRepository
import kotlinx.coroutines.CoroutineDispatcher

class CheckNetworkPathAvailableUseCase(
    dispatcher: CoroutineDispatcher,
    private val networkRepository: NetworkRepository
) : UseCase<CheckNetworkPathAvailableUseCase.Params, Boolean>(dispatcher) {
    override suspend fun run(params: Params?): DomainResult<Boolean> {
        // Thread.sleep(10000)
        return params?.let {
            DomainResult.Success(
                networkRepository.checkNetworkPathAvailable(params.path)
            )
        } ?: DomainResult.Error(DomainResult.UnknownException)
    }
    data class Params(
        val path: String
    )
}