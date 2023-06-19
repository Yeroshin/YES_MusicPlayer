package com.yes.trackdialogfeature.domain.repository

interface MediaRepository {
    data class Params(
        val projection: Array<String?>,
        val selection: String?,
        val selectionArgs: Array<String>?
    )
}