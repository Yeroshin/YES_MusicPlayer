package com.yes.trackdialogfeature.domain.common

sealed interface UseCaseException {
    class UnknownException(cause: String) : UseCaseException
}