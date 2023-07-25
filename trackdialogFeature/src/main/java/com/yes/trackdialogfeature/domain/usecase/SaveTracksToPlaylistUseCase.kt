package com.yes.trackdialogfeature.domain.usecase
import com.yes.trackdialogfeature.data.repository.MediaRepositoryImpl
import com.yes.trackdialogfeature.domain.repository.IPlayListDao
import com.yes.trackdialogfeature.domain.repository.ISettingsRepository
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.trackdialogfeature.domain.usecase.SaveTracksToPlaylistUseCase.Params
import kotlinx.coroutines.CoroutineDispatcher

class SaveTracksToPlaylistUseCase(
    dispatcher: CoroutineDispatcher,
    private val mediaRepositoryImpl: MediaRepositoryImpl,
    private val playListRepository: IPlayListDao,
    private val settingsRepository:ISettingsRepository
): UseCase<Params, List<Long>>(dispatcher) {
    override fun run(params: Params?): DomainResult<List<Long>> {
        val selectedItems=params?.items?.filter {
            it.selected
        }
        val tracks=selectedItems?.onEach{
            mediaRepositoryImpl.getAudioItems(
                it.type,
                it.name
            )
        }
      /*  val mediaTracks=mediaRepositoryImpl.getMenuItems()
        playListRepository.saveTracks()*/
        return DomainResult.Success(listOf(10))
    }
    data class Params(val items:List<Item>)
}
