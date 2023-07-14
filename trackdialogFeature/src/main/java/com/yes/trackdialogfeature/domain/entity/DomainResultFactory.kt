package com.yes.trackdialogfeature.domain.entity

class DomainResultFactory {
    companion object {
        fun <T> createSuccess(data: T): DomainResult<T> {
            return DomainResult.Success(data)
        }
    }
}