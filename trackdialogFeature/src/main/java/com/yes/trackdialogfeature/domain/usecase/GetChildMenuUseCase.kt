package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.repository.MenuRepository
import com.yes.trackdialogfeature.domain.common.Result
import com.yes.trackdialogfeature.domain.common.UseCaseException
import com.yes.trackdialogfeature.domain.entity.Menu
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay

class GetChildMenuUseCase(
    dispatcher: CoroutineDispatcher,
    private val menuRepository: MenuRepository
    ) : UseCase<GetChildMenuUseCase.Params, Menu>(dispatcher) {

    override fun run(params: Params): Result<Menu> {
        return if(params.title.equals("root")){
            menuRepository.getMenu()
        }else{

            menuRepository.getMenu(params.title, params.name)
        }
    }

    data class Params(val title:String, val name:String)

}