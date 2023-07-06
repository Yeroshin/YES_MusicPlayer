package com.yes.trackdialogfeature.domain.usecase
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.usecase.SaveTrackToPlaylistUseCase.Params
import kotlinx.coroutines.CoroutineDispatcher

class SaveTrackToPlaylistUseCase(
    dispatcher: CoroutineDispatcher
): UseCase<Params, Int>(dispatcher) {
    override fun run(params: Params): DomainResult<Int> {
        TODO("Not yet implemented")
    }
    data class Params(val id:Int, val name:String)
}
