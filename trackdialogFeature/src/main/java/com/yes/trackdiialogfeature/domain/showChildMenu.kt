package com.yes.trackdiialogfeature.domain

class showChildMenu(private val mediaRepository: IMenuRepository) : UseCase<Menu?, Menu>() {
    override suspend fun run(menu: Menu?): Menu {
        return if (menu!=null){
            Menu("null",menu)
        }else{
            mediaRepository.getRootMenu()
        }
    }
}