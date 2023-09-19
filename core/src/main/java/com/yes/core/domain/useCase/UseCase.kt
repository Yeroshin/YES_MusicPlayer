package com.yes.core.domain.useCase

import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.*

abstract class UseCase<REQUEST, MODEL>(
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(params: REQUEST?): DomainResult<MODEL> {

        return withContext(dispatcher) {
          //  delay(5000)
            try {
                run(params)
            }catch (exception: Exception){
                DomainResult.Error(DomainResult.UnknownException)
            }
        }
    }

    abstract fun run(params: REQUEST?): DomainResult<MODEL>

}