package com.yes.trackdiialogfeature.domain

import android.net.Uri
import com.yes.coreui.BaseAdapterItem

class MediaItem (val type:String,val title: String){
    var selected: Boolean=false
    var activated: Boolean=false


    var uri: Uri?=null

   // var artist: String?=null
  //  var album: String?=null
   //var genre: String?=null
    var duration: Int?=null
    var size: Int?=null

}