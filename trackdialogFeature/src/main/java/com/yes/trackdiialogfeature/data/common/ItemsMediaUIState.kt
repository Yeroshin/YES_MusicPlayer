package com.yes.trackdiialogfeature.data.common

import com.yes.trackdiialogfeature.domain.MediaItem

sealed class ItemsMediaUIState {
    data class Success(val items:List<MediaItem>):ItemsMediaUIState()
}