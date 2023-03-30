package com.yes.trackdialogfeature.domain.entity

sealed class MenuException:DomainResult.DomainException {
    object Empty:MenuException()

}