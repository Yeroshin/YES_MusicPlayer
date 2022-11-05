package com.yes.core.domain.models

class PlayList {
    private lateinit var name:String
    private var currentTrack:Int=0
    private lateinit var tracks:ArrayList<Track>
}