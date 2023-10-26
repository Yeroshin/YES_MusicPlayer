package com.yes.core.repository.entity

import androidx.media3.common.MediaMetadata

data class PlayerStateDataSourceEntity (
    val mediaMetadata: MediaMetadata?=null,
    val stateBuffering:Boolean=false
)