package com.yes.trackdiialogfeature.data.repository.entity

import android.net.Uri

class MediaEntity() {
    var categoryName: String?=null
    var projection: String?=null
    var where: String?=null
    var what: String?=null

    var uri: Uri?=null
    var title: String?=null
    var artist: String?=null
    var album: String?=null
    var genre: String?=null
    var duration: Int?=null
    var size: Int?=null
}

