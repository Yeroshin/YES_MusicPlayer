package com.yes.trackdiialogfeature.domain

class MenuInteractor(private val mediaRepository: IMenuRepository) {

    fun showParentMenu(menu:Menu):Menu{
        TODO()
    }

    fun showChildMenu(menu:Menu?):Menu{
        if (menu!=null){
            return Menu("null",null)
        }else{
            return mediaRepository.getRootMenu()
        }

    }

    fun saveMenuSelectedTracks(menu:Menu):Menu{
        TODO()
    }
}