package com.yes.trackdialogfeature.domain.entity

import com.yes.core.domain.models.DomainResult

sealed class MenuException: DomainResult.DomainException {
    object Empty:MenuException()

}