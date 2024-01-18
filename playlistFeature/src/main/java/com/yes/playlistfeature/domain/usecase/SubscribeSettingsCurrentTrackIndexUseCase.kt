package com.yes.playlistfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistfeature.data.repository.PlayerRepository
import com.yes.playlistfeature.data.repository.SettingsRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter

class SubscribeSettingsCurrentTrackIndexUseCase(
    dispatcher: CoroutineDispatcher,
    private val playerRepository: PlayerRepository,
    private val settingsRepository: SettingsRepositoryImpl
) : UseCase<Any, Flow<Int>>(dispatcher) {
    override suspend fun run(params: Any?): DomainResult<Flow<Int>> {
        return DomainResult.Success(
            settingsRepository.subscribeCurrentTrackIndex().filter { index -> index != -1 }
        )
    }

    /*  override suspend fun run(params: Any?): DomainResult<Flow<Int>> {
          return DomainResult.Success(
              settingsRepository.subscribeCurrentTrackIndex()
                  .flatMapMerge { settingsIndex ->
                      flow {
                          if (settingsIndex != -1) {
                              emit(settingsIndex)
                          }
                          emitAll(
                              playerRepository.subscribeCurrentTrackIndex()
                                  .filter { trackIndex -> trackIndex != -1 }
                                  .onEach { trackIndex ->
                                    //  settingsRepository.setCurrentTrackIndex(trackIndex)
                                  }
                          )

                      }
                  }
          )
      }*/
    /* override suspend fun run(params: Any?): DomainResult<Flow<Int>> {
         return DomainResult.Success(
             settingsRepository.subscribeCurrentTrackIndex()
                 .flatMapMerge { settingsIndex ->
                     flow {
                         if (settingsIndex != -1) {
                             emit(settingsIndex)
                         }
                         emitAll(
                             playerRepository.subscribeCurrentTrackIndex()
                                 .filter { trackIndex -> trackIndex != -1 }
                                 .onEach { trackIndex ->
                                     settingsRepository.setCurrentTrackIndex(trackIndex)
                                 }
                         )

                     }
                 }
         )
     }*/
}