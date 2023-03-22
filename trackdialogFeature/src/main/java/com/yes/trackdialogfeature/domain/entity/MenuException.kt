package com.yes.trackdialogfeature.domain.entity

sealed class MenuException:Exception() {
    object Empty:MenuException()
    object UnknownException:MenuException()
}