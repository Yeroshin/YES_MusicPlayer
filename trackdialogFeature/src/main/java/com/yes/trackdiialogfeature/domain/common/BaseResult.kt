package com.yes.trackdiialogfeature.domain.common

sealed class BaseResult<out T> {
    data class Success<out T>(val data: T) : BaseResult<T>()
    data class Failure(val throwable: Throwable?) : BaseResult<Nothing>()
}