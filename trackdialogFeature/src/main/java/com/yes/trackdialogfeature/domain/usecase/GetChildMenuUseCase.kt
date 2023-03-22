package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.repository.MenuRepository
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import kotlinx.coroutines.CoroutineDispatcher

class GetChildMenuUseCase(
    dispatcher: CoroutineDispatcher,
    private val menuRepository: MenuRepository
    ) : UseCase<GetChildMenuUseCase.Params, Menu>(dispatcher) {

    override fun run(params: Params): DomainResult<Menu> {
        return if(params.title.equals("root")){
            menuRepository.getMenu()
        }else{
            menuRepository.getMenu(params.title, params.name)
        }
    }

    data class Params(val title:String, val name:String)

}