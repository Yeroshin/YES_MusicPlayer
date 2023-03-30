package com.yes.trackdialogfeature.domain.usecase

import kotlinx.coroutines.*
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.MenuException

abstract class UseCase<REQUEST, MODEL>(
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(params: REQUEST): DomainResult<MODEL> {
        return withContext(dispatcher) {
            try {
                run(params)
            }catch (exception: Exception){
                DomainResult.Error(DomainResult.UnknownException)
            }

        }
    }

    abstract fun run(params: REQUEST): DomainResult<MODEL>

}