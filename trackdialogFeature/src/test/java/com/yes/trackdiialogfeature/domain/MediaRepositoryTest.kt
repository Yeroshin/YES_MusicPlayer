package com.yes.trackdiialogfeature.domain

class MediaRepositoryTest(

) : IMenuRepository {


    override fun getMedia(
        type: String?,
        what: String?,
        where: ArrayList<String>
    ): ArrayList<MediaItem> {
        // audioDataStore.getMedia(type,what,where)
        return arrayListOf(MediaItem("", ""))
    }
}


