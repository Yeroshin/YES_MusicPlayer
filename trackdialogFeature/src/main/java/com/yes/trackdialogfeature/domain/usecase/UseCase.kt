package com.yes.trackdialogfeature.domain.usecase

import kotlinx.coroutines.*
import com.yes.trackdialogfeature.domain.common.Result

abstract class UseCase<REQUEST,MODEL> {
    suspend operator fun invoke(params: REQUEST): Result<MODEL> {
        return withContext(Dispatchers.IO) {
            run(params)
        }
    }

    abstract fun run(params: REQUEST): Result<MODEL>

}