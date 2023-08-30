package com.yes.trackdialogfeature.data.repository.entity

data class MediaDataStoreEntity(
    val title:String,
    val artist:String="",
    val album:String="",
    val duration:Long=0,
    val data:String="",//path to
    val size:Long=0,
)