package com.yes.core.di.module

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.yes.core.data.dataSource.SettingsDataSource
import com.yes.core.data.dataSource.SettingsDataStore
import com.yes.core.data.mapper.Mapper
import com.yes.core.data.repository.PlayListRepositoryImpl
import com.yes.core.data.repository.SettingsRepositoryImpl
import com.yes.core.domain.repository.IPlayListDao
import com.yes.core.domain.useCase.GetCurrentTrackIndexUseCase
import com.yes.core.domain.useCase.SetSettingsTrackIndexUseCase
import com.yes.core.domain.useCase.SubscribeCurrentPlaylistTracksUseCase
import com.yes.core.presentation.MusicService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher

@Module
class MusicServiceModule {


    @Provides
    fun providesMediaSession(
        context: Context,
        player: ExoPlayer
    ): MediaSession {
        return MediaSession.Builder(context, player).build()
    }
    @Provides
    fun providesSubscribeCurrentPlaylistTracksUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        playListRepositoryImpl: PlayListRepositoryImpl,
        settingsRepository: SettingsRepositoryImpl
    ): SubscribeCurrentPlaylistTracksUseCase {
        return SubscribeCurrentPlaylistTracksUseCase(
            dispatcher,
            playListRepositoryImpl,
            settingsRepository
        )
    }
    @Provides
    fun providesMapper(
    ): Mapper {
        return Mapper()
    }
    @Provides
    fun providesSettingsRepository(
        settingsDataSource: SettingsDataSource
    ): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(
            settingsDataSource
        )
    }
    @Provides
    fun providesPlayListRepository(
        mapper: Mapper,
        playListDao: IPlayListDao,
    ): PlayListRepositoryImpl {
        return PlayListRepositoryImpl(
            mapper,
            playListDao,
        )
    }
    @Provides
    fun providesGetCurrentTrackIndexUseCase(
        @IoDispatcher dispatcher: CoroutineDispatcher,
        settingsRepositoryImpl: SettingsRepositoryImpl,
    ): GetCurrentTrackIndexUseCase {
        return GetCurrentTrackIndexUseCase(
            dispatcher,
            settingsRepositoryImpl,
        )
    }

  /*  @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    @Provides
    fun  providesPlayer(
        context: Context,
        audioSessionId:Int
    ): ExoPlayer {
        val player=ExoPlayer.Builder(context).build()
        player.audioSessionId=audioSessionId
        return player
    }*/


  /* @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
   @Provides
   fun  providesExoPlayer(
       context: Context,
       rendererFactory:RendererFactory
   ): ExoPlayer {
       return  ExoPlayer.Builder(context)
          .setRenderersFactory(rendererFactory)
           .build()
   }*/
  @Provides
  fun providesSetSettingsTrackIndexUseCase(
      @IoDispatcher dispatcher: CoroutineDispatcher,
      settingsRepositoryImpl: SettingsRepositoryImpl,
  ): SetSettingsTrackIndexUseCase {
      return SetSettingsTrackIndexUseCase(
          dispatcher,
          settingsRepositoryImpl,
      )
  }
    @Provides
    fun providesDependency(
        mediaSession: MediaSession,
        mapper: Mapper,
        subscribeCurrentPlaylistTracksUseCase:SubscribeCurrentPlaylistTracksUseCase,
        getCurrentTrackIndexUseCase: GetCurrentTrackIndexUseCase,
        setSettingsTrackIndexUseCase: SetSettingsTrackIndexUseCase
    ): MusicService.Dependency {
        return MusicService.Dependency(
            mediaSession,
            mapper,
            subscribeCurrentPlaylistTracksUseCase,
            getCurrentTrackIndexUseCase,
            setSettingsTrackIndexUseCase
        )
    }
}