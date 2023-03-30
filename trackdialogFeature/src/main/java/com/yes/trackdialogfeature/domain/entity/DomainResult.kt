package com.yes.trackdialogfeature.domain.entity

sealed class DomainResult<out T> {
    data class Success<T>(val data: T) : DomainResult<T>()
    data class Error(val exception: DomainException) : DomainResult<Nothing>()
    interface DomainException
    object UnknownException:DomainException
}