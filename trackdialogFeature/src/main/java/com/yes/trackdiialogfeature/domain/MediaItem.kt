package com.yes.trackdiialogfeature.domain

import android.net.Uri
import com.yes.coreui.BaseAdapterItem

class MediaItem {
    var selected: Boolean=false
    var activated: Boolean=false
    var type:String?=null

    var uri: Uri?=null
    var title: String?=null
   // var artist: String?=null
  //  var album: String?=null
   //var genre: String?=null
    var duration: Int?=null
    var size: Int?=null

}