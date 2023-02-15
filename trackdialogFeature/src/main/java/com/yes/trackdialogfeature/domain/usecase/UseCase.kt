package com.yes.trackdialogfeature.domain.usecase

import android.util.Log
import kotlinx.coroutines.*
import com.yes.trackdialogfeature.domain.common.Result
import com.yes.trackdialogfeature.domain.common.UseCaseException

abstract class UseCase<REQUEST, MODEL>(
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(params: REQUEST): Result<MODEL> {
        return withContext(dispatcher) {
            try {
                run(params)
            }catch (e: Exception){
                Result.Error(UseCaseException.UnknownException)
            }

        }
    }

    abstract fun run(params: REQUEST): Result<MODEL>

}