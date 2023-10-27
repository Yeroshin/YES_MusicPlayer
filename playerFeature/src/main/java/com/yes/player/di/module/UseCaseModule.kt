package com.yes.player.di.module

import com.yes.core.di.module.IoDispatcher
import com.yes.player.data.repository.PlayerRepository
import com.yes.player.domain.usecase.SeekUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class UseCaseModule {
    @Provides
    fun providesSeekUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): SeekUseCase {
        return SeekUseCase(
            dispatcher,
            playerRepository
        )
    }
}