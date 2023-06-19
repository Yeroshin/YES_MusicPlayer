package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.domain.repository.MediaRepository
import com.yes.trackdialogfeature.domain.repository.MediaRepository.Params
class MediaRepositoryImpl(
    private val audioDataStore: AudioDataStore
) : MediaRepository{
    fun getMedia(params:Params){

    }
}