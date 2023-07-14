package com.yes.trackdialogfeature.domain.usecase
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase.Params
import kotlinx.coroutines.CoroutineDispatcher

class SaveTracksToPlaylistUseCase(
    dispatcher: CoroutineDispatcher
): UseCase<Params, Boolean>(dispatcher) {
    override fun run(params: Params): DomainResult<Boolean> {
        TODO("Not yet implemented")
    }
    data class Params(val items:List<Item>)
}
