package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.repository.MenuRepository
import com.yes.trackdialogfeature.domain.common.Result
import com.yes.trackdialogfeature.domain.common.UseCaseException
import com.yes.trackdialogfeature.domain.entity.Menu

class GetChildMenuUseCase(
    private val menuRepository: MenuRepository
    ) : UseCase<GetChildMenuUseCase.Params, Menu>() {

    override fun run(params: Params): Result<Menu> {
        if(true){
            return Result.Success(menuRepository.getMenu(params.title, params.name))
        }else{
            return Result.Error(UseCaseException.UnknownException(""))
        }
    }

    data class Params(val title:String, val name:String)

}