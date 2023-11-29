package com.yes.playlistfeature.domain.usecase

import com.yes.core.domain.models.DomainResult
import com.yes.core.domain.useCase.UseCase
import com.yes.playlistfeature.data.repository.PlayListRepositoryImpl
import com.yes.playlistfeature.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import com.yes.playlistfeature.domain.usecase.ChangeTracksPositionUseCase.Params
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull


class ChangeTracksPositionUseCase(
    dispatcher: CoroutineDispatcher,
    private val playListRepository: PlayListRepositoryImpl,
    private val settingsRepository: SettingsRepository
) : UseCase<Params, Boolean>(dispatcher) {


    override suspend fun run(params: Params?): DomainResult<Boolean> {
        return params?.let {
            playListRepository.subscribeTracksWithPlaylistId(
                settingsRepository.subscribeCurrentPlaylistId().first()
            )
                .firstOrNull()
                ?.sortedBy { it.position }
                ?.toMutableList()
                ?.let {
                    val element = it.find { it.position == params.from } // Находим элемент, который нужно переместить
                    if (element != null) {
                        it.remove(element) // Удаляем элемент из текущей позиции
                        it.add(params.to , element) // Добавляем элемент на новую позицию
                        element.position = params.to
                        // Обновляем порядковые значения элементов
                        if (params.from < params.to) {
                            for (i in params.from until params.to) {
                                it[i].copy(position = i)
                            }
                            it.subList(params.from , params.to)
                                .forEach{
                                    playListRepository.updateTrack(it)
                                }
                        } else {
                            for (i in params.to until params.from) {
                                it[i].copy(position = i + 1)
                            }
                            it.subList(params.to,params.from )
                                .forEach{
                                    playListRepository.updateTrack(it)
                                }
                        }
                        // Устанавливаем новый порядковый номер перемещенному элементу

                    }
                }

            DomainResult.Success(true)
        } ?: DomainResult.Error(DomainResult.UnknownException)
    }
    class Params(val from:Int,val to:Int)
}