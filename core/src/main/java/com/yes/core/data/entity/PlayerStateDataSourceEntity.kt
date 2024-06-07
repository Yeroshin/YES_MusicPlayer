package com.yes.core.data.entity

import androidx.media3.common.MediaMetadata

data class PlayerStateDataSourceEntity (
    val mediaMetadata: MediaMetadata?=null,
    val stateBuffering:Boolean=false,
    val duration:Long?=null,
    val isPlaying:Boolean?=null
)