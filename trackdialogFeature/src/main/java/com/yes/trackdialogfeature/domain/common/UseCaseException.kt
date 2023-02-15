package com.yes.trackdialogfeature.domain.common

sealed interface UseCaseException {
    object UnknownException : UseCaseException
}