package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.domain.IMediaRepository
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.repository.IMenuRepository
import com.yes.trackdialogfeature.domain.repository.MenuRepository
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase.Params
import kotlinx.coroutines.CoroutineDispatcher

class GetMenuUseCase(
    dispatcher: CoroutineDispatcher,
    private val menuRepository: IMenuRepository,
) : UseCase<Params,Menu>(dispatcher) {
    override fun run(params: Params): DomainResult<Menu> {
        return if(params.id == 0){
            menuRepository.getRootMenu()
        }else{
            menuRepository.getChildMenu(params.id,params.name)
        }
    }
    data class Params(
        val id: Int,
        val name: String
    )
}