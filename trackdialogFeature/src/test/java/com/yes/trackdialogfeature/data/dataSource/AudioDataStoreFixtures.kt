package com.yes.trackdialogfeature.data.dataSource

import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity

object AudioDataStoreFixtures {

    fun getArtists():Array<String>{
        return arrayOf("Dire Straits","Chris Rea")
    }
    fun getAlbums():Array<String>{
        return arrayOf("Brothers in Arms","Love over Gold")
    }
    fun getTracks():Array<String>{
        return arrayOf("Money for Nothing","Your Latest Trick")
    }

}