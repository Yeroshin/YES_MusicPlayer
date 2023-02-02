package com.yes.trackdialogfeature.domain.common

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val data: UseCaseException) : Result<Nothing>()
}