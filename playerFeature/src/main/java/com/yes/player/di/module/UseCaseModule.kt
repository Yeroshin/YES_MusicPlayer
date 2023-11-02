package com.yes.player.di.module

import com.yes.core.di.module.IoDispatcher
import com.yes.core.di.module.MainDispatcher
import com.yes.player.data.factory.VisualizerFactory
import com.yes.player.data.repository.PlayerRepository
import com.yes.player.data.repository.PlaylistRepositoryImpl
import com.yes.player.data.repository.SettingsRepositoryImpl
import com.yes.player.data.repository.VisualizerRepository
import com.yes.player.domain.usecase.PlayUseCase
import com.yes.player.domain.usecase.SeekToNextUseCase
import com.yes.player.domain.usecase.SeekToPreviousUseCase
import com.yes.player.domain.usecase.SeekUseCase
import com.yes.player.domain.usecase.SubscribeCurrentPlaylistUseCase
import com.yes.player.domain.usecase.SubscribeDurationCounterUseCase
import com.yes.player.domain.usecase.SubscribePlayerStateUseCase
import com.yes.player.domain.usecase.SubscribeVisualizerUseCase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class UseCaseModule {

    @Provides
    fun providesSubscribeVisualizerUseCase(
        @MainDispatcher dispatcher: CoroutineDispatcher,
         playerRepository: PlayerRepository,
         visualizerRepository: VisualizerRepository
    ): SubscribeVisualizerUseCase {
        return SubscribeVisualizerUseCase(
            dispatcher,
            playerRepository,
            visualizerRepository
        )
    }

    @Provides
    fun providesSeekUseCase(
        @MainDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): SeekUseCase {
        return SeekUseCase(
            dispatcher,
            playerRepository
        )
    }
    @Provides
    fun providesSubscribeDurationCounterUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): SubscribeDurationCounterUseCase {
        return SubscribeDurationCounterUseCase(
            dispatcher,
            playerRepository
        )
    }
    @Provides
    fun providesPlayUseCase(
        @MainDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): PlayUseCase {
        return PlayUseCase(
            dispatcher,
            playerRepository
        )
    }
    @Provides
    fun providesSeekToNextUseCase(
        @MainDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): SeekToNextUseCase {
        return SeekToNextUseCase(
            dispatcher,
            playerRepository
        )
    }
    @Provides
    fun providesSubscribeCurrentPlaylistUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        playListRepositoryImpl: PlaylistRepositoryImpl,
        settingsRepository: SettingsRepositoryImpl
    ): SubscribeCurrentPlaylistUseCase {
        return SubscribeCurrentPlaylistUseCase(
            dispatcher,
            playListRepositoryImpl,
            settingsRepository
        )
    }
    @Provides
    fun providesSubscribeCurrentTrackInfoUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): SubscribePlayerStateUseCase {
        return SubscribePlayerStateUseCase(
            dispatcher,
            playerRepository
        )
    }
    @Provides
    fun providesSeekToPreviousUseCase(
        @MainDispatcher dispatcher: CoroutineDispatcher,
        playerRepository: PlayerRepository
    ): SeekToPreviousUseCase {
        return SeekToPreviousUseCase(
            dispatcher,
            playerRepository
        )
    }
}