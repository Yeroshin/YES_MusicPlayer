package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.repository.MenuRepository
import kotlinx.coroutines.CoroutineDispatcher

class GetChildMenuUseCase(
    dispatcher: CoroutineDispatcher,
    private val menuRepository: MenuRepository
    ) : UseCase<GetChildMenuUseCase.Params, Menu>(dispatcher) {

    override fun run(params: Params): DomainResult<Menu> {
        return if(params.id==0){
            menuRepository.getMenu()
        }else{
            menuRepository.getMenu(params.id, params.name)
        }
    }

    data class Params(val id:Int, val name:String)

}