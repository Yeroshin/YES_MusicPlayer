package com.yes.core.domain.useCase

import com.yes.core.domain.models.DomainResult

abstract class SynchroUseCase<REQUEST, RESULT>  {
    operator fun invoke(params: REQUEST): DomainResult<RESULT> {
        return try {
            run(params)
        } catch (exception: Exception) {
            DomainResult.Error(DomainResult.UnknownException)
        }
    }

    operator fun invoke(): DomainResult<RESULT> {
        return try {
            run(null)
        } catch (exception: Exception) {
            DomainResult.Error(DomainResult.UnknownException)
        }
    }
    abstract fun run(params: REQUEST?): DomainResult<RESULT>
}