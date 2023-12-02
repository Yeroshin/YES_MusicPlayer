package com.yes.playlistdialogfeature.domain.entity

import com.yes.core.domain.models.DomainResult

sealed class PlaylistException: DomainResult.DomainException {
    data object PlaylistsSizeLimit:PlaylistException()

}