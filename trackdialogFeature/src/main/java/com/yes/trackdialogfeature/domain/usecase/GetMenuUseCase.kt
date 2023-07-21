package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.data.repository.MenuRepositoryImpl
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.MenuException
import com.yes.trackdialogfeature.domain.usecase.GetMenuUseCase.Params
import kotlinx.coroutines.CoroutineDispatcher

class GetMenuUseCase(
    dispatcher: CoroutineDispatcher,
    private val menuRepository: MenuRepositoryImpl,
    private val mediaRepository: MediaRepositoryImpl
) : UseCase<Params, Menu>(dispatcher) {
    override fun run(params: Params?): DomainResult<Menu> {
        params?.let {
            val currentItem = menuRepository.getItem(params.id)
            val childPrimaryItem = menuRepository.getChildItem(params.id)
                ?: return DomainResult.Error(MenuException.Empty)
            val childItems = mediaRepository.getMenuItems(
                childPrimaryItem.id,
                childPrimaryItem.type ?: return DomainResult.Error(DomainResult.UnknownException),
                currentItem.type,
                params.name
            )
            return DomainResult.Success(
                Menu(
                    params.name,
                    childItems
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