package com.yes.trackdialogfeature.data.common

import com.yes.trackdialogfeature.domain.entity.MediaItem

sealed class ItemsMediaUIState {
    data class Success(val items:List<MediaItem>):ItemsMediaUIState()
}