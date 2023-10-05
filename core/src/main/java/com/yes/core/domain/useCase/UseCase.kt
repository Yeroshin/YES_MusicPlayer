package com.yes.core.domain.useCase

import com.yes.core.domain.models.DomainResult
import kotlinx.coroutines.*

abstract class UseCase<REQUEST, RESULT>(
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(params: REQUEST?=null): DomainResult<RESULT> {

        return withContext(dispatcher) {
          //  delay(5000)
            try {
                run(params)
            }catch (exception: Exception){
                DomainResult.Error(DomainResult.UnknownException)
            }
        }
    }
    suspend operator fun invoke(): DomainResult<RESULT> {

        return withContext(dispatcher) {
            //  delay(5000)
            try {
                run(null)
            }catch (exception: Exception){
                DomainResult.Error(DomainResult.UnknownException)
            }
        }
    }
    abstract fun run(params: REQUEST?): DomainResult<RESULT>

}