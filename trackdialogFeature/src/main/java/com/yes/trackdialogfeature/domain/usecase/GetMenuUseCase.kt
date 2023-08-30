package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.data.repository.MenuRepositoryImpl
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.MenuException
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase.Params
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay

class GetMenuUseCase(
    dispatcher: CoroutineDispatcher,
    private val menuRepository: MenuRepositoryImpl,
    private val mediaRepository: MediaRepositoryImpl
) : UseCase<Params, Menu>(dispatcher) {
    override fun run(params: Params?): DomainResult<Menu> {
       // Thread.sleep(10000)

        params?.let {
            val currentMenu=menuRepository.getItem(params.id)?:return DomainResult.Error(DomainResult.UnknownException)
            val childMenu=menuRepository.getChildItem(params.id)?:return DomainResult.Error(MenuException.Empty)
            val childItems=mediaRepository.getMenuItems(
                childMenu.type?:return DomainResult.Error(DomainResult.UnknownException),
                currentMenu.type,
                params.name
            )
            return DomainResult.Success(
                Menu(
                    params.name,
                    childItems.map { it.copy(menuId = childMenu.menuId, type = childMenu.type) }
                )
            )
        } ?: run {
            return menuRepository.getRootMenu()?.let {
                DomainResult.Success(it)
            }?:DomainResult.Error(DomainResult.UnknownException)
        }
    }

    data class Params(
        val id: Int,
        val name: String
    )
}