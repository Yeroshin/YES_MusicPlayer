package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.data.repository.MenuRepositoryImpl
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase.Params
import kotlinx.coroutines.CoroutineDispatcher

class GetMenuUseCase(
    dispatcher: CoroutineDispatcher,
    private val menuRepository: MenuRepositoryImpl,
    private val mediaRepository: MediaRepositoryImpl
) : UseCase<Params, Menu>(dispatcher) {
    override fun run(params: Params?): DomainResult<Menu> {
        return params
            ?.let {
                menuRepository.getChildMenu(params.id)
                    ?.let {
                        val childItem=menuRepository.getChildItem(params.id)
                        it.children.toMutableList().addAll(
                            mediaRepository.getMenuItems(
                                childItem
                                    ?.id
                                    ?: ,
                                childItem.type
                            )
                        )
                        DomainResult.Success(it)
                    }
                    ?:run{
                        return DomainResult.Error(DomainResult.UnknownException)
                    }
            }
            ?: run {
                menuRepository.getRootMenu()
                    ?.let {
                        return DomainResult.Success(it)
                    }
                    ?:run{
                        return DomainResult.Error(DomainResult.UnknownException)
                    }


            }
    }

    data class Params(
        val id: Int,
        val name: String
    )
}