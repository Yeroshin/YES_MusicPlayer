package com.yes.playlistfeature.domain.entity

sealed class Mode{
    data object ShuffleMode:Mode()
    data object RepeatMode:Mode()
    data object SequentialMode:Mode()

}
