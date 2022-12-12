package com.yes.trackdiialogfeature.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase <out Type>{
    abstract suspend fun run(): Type

    operator fun invoke(
        scope: CoroutineScope,
        onResult: (Type) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run()
            }

            onResult(deferred.await())
        }
        

    }
}