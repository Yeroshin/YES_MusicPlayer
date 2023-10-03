package com.example.musicplayerfeature.media



import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.CommandButton
import androidx.media3.session.LibraryResult
import androidx.media3.session.LibraryResult.RESULT_ERROR_NOT_SUPPORTED
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.*
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture


private const val MY_MEDIA_ROOT_ID = "media_root_id"
private const val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"
class MusicService: MediaLibraryService() {
    companion object {
        private const val SEARCH_QUERY_PREFIX_COMPAT = "androidx://media3-session/playFromSearch"
        private const val SEARCH_QUERY_PREFIX = "androidx://media3-session/setMediaUri"
        private const val CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON =
            "android.media3.session.demo.SHUFFLE_ON"
        private const val CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF =
            "android.media3.session.demo.SHUFFLE_OFF"
        private const val NOTIFICATION_ID = 123
        private const val CHANNEL_ID = "demo_session_notification_channel_id"
        private val immutableFlag = if (Build.VERSION.SDK_INT >= 23) FLAG_IMMUTABLE else 0
    }
    private lateinit var customCommands: List<CommandButton>
    private lateinit var mediaLibrarySession: MediaLibrarySession
    private lateinit var player: ExoPlayer
    private val librarySessionCallback = CustomMediaLibrarySessionCallback()
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession {
        return mediaLibrarySession
    }

    // Create your player and media session in the onCreate lifecycle event
    override fun onCreate() {
        super.onCreate()
        customCommands =
            listOf(
                getShuffleCommandButton(
                    SessionCommand(CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON, Bundle.EMPTY)
                ),
                getShuffleCommandButton(
                    SessionCommand(CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF, Bundle.EMPTY)
                )
            )
        initializeSessionAndPlayer()
       // setListener(MediaSessionServiceListener())
    }
    private fun getShuffleCommandButton(sessionCommand: SessionCommand): CommandButton {
        val isOn = sessionCommand.customAction == CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON
        return CommandButton.Builder()
           /* .setDisplayName(
                getString(
                    if (isOn) R.string.exo_controls_shuffle_on_description
                    else R.string.exo_controls_shuffle_off_description
                )
            )*/
            .setSessionCommand(sessionCommand)
           // .setIconResId(if (isOn) R.drawable.exo_icon_shuffle_off else R.drawable.exo_icon_shuffle_on)
            .build()
    }


    private fun initializeSessionAndPlayer() {
        player =
            ExoPlayer.Builder(this)
                .setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true)
                .build()
      //  MediaItemTree.initialize(assets)

        mediaLibrarySession =
            MediaLibrarySession.Builder(this, player, librarySessionCallback)
               // .setSessionActivity(getSingleTopActivity())
             //   .setCustomLayout(ImmutableList.of(customCommands[0]))
               // .setBitmapLoader(CacheBitmapLoader(DataSourceBitmapLoader(/* context= */ this)))
                .build()
    }
   /* private fun getSingleTopActivity(): PendingIntent {
        return getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            immutableFlag or FLAG_UPDATE_CURRENT
        )
    }*/
    private inner class CustomMediaLibrarySessionCallback : MediaLibrarySession.Callback {

     /*   override fun onConnect(session: MediaSession, controller: ControllerInfo): ConnectionResult {
            val availableSessionCommands =
                ConnectionResult.DEFAULT_SESSION_AND_LIBRARY_COMMANDS.buildUpon()
            for (commandButton in customCommands) {
                // Add custom command to available session commands.
                commandButton.sessionCommand?.let { availableSessionCommands.add(it) }
            }
            return ConnectionResult.AcceptedResultBuilder(session)
                .setAvailableSessionCommands(availableSessionCommands.build())
                .build()
        }*/

      /*  override fun onCustomCommand(
            session: MediaSession,
            controller: ControllerInfo,
            customCommand: SessionCommand,
            args: Bundle
        ): ListenableFuture<SessionResult> {
            if (CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_ON == customCommand.customAction) {
                // Enable shuffling.
                player.shuffleModeEnabled = true
                // Change the custom layout to contain the `Disable shuffling` command.
                session.setCustomLayout(ImmutableList.of(customCommands[1]))
            } else if (CUSTOM_COMMAND_TOGGLE_SHUFFLE_MODE_OFF == customCommand.customAction) {
                // Disable shuffling.
                player.shuffleModeEnabled = false
                // Change the custom layout to contain the `Enable shuffling` command.
                session.setCustomLayout(ImmutableList.of(customCommands[0]))
            }
            return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
        }*/
        fun tmpGetItem():MediaItem{
          val metadata =
          MediaMetadata.Builder()
             /* .setAlbumTitle(album)
              .setTitle(title)
              .setArtist(artist)
              .setGenre(genre)


              .setArtworkUri(imageUri)
              .setMediaType(mediaType)*/
              .setIsPlayable(true)
                  .setIsBrowsable(false)
              .build()
            return MediaItem.Builder()
                .setMediaId("mediaId")
               // .setSubtitleConfigurations(subtitleConfigurations)
                .setMediaMetadata(metadata)
                .setUri(
                    "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3"
                )
                .build()
        }
        override fun onGetLibraryRoot(
            session: MediaLibrarySession,
            browser: ControllerInfo,
            params: LibraryParams?
        ): ListenableFuture<LibraryResult<MediaItem>> {
            if (params != null && params.isRecent) {
                // The service currently does not support playback resumption. Tell System UI by returning
                // an error of type 'RESULT_ERROR_NOT_SUPPORTED' for a `params.isRecent` request. See
                // https://github.com/androidx/media/issues/355
                return Futures.immediateFuture(LibraryResult.ofError(RESULT_ERROR_NOT_SUPPORTED))
            }
            return Futures.immediateFuture(LibraryResult.ofItem(
                tmpGetItem()
                , params))
        }

        override fun onGetItem(
            session: MediaLibrarySession,
            browser: ControllerInfo,
            mediaId: String
        ): ListenableFuture<LibraryResult<MediaItem>> {
            val item =tmpGetItem()
               /* MediaItemTree.getItem(mediaId)
                    ?: return Futures.immediateFuture(
                        LibraryResult.ofError(LibraryResult.RESULT_ERROR_BAD_VALUE)
                    )*/
            return Futures.immediateFuture(LibraryResult.ofItem(item, /* params= */ null))
        }

        override fun onSubscribe(
            session: MediaLibrarySession,
            browser: ControllerInfo,
            parentId: String,
            params: LibraryParams?
        ): ListenableFuture<LibraryResult<Void>> {
            val children = listOf(MediaItem.Builder().setUri(
                "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3"
            ).build())
               /* MediaItemTree.getChildren(parentId)
                    ?: return Futures.immediateFuture(
                        LibraryResult.ofError(LibraryResult.RESULT_ERROR_BAD_VALUE)
                    )*/
            session.notifyChildrenChanged(browser, parentId, children.size, params)
            return Futures.immediateFuture(LibraryResult.ofVoid())
        }

      /*  override fun onGetChildren(
            session: MediaLibrarySession,
            browser: ControllerInfo,
            parentId: String,
            page: Int,
            pageSize: Int,
            params: LibraryParams?
        ): ListenableFuture<LibraryResult<ImmutableList<MediaItem>>> {
            val children =
                MediaItemTree.getChildren(parentId)
                    ?: return Futures.immediateFuture(
                        LibraryResult.ofError(LibraryResult.RESULT_ERROR_BAD_VALUE)
                    )

            return Futures.immediateFuture(LibraryResult.ofItemList(children, params))
        }

        override fun onAddMediaItems(
            mediaSession: MediaSession,
            controller: MediaSession.ControllerInfo,
            mediaItems: List<MediaItem>
        ): ListenableFuture<List<MediaItem>> {
            val updatedMediaItems: List<MediaItem> =
                mediaItems.map { mediaItem ->
                    if (mediaItem.requestMetadata.searchQuery != null)
                        getMediaItemFromSearchQuery(mediaItem.requestMetadata.searchQuery!!)
                    else MediaItemTree.getItem(mediaItem.mediaId) ?: mediaItem
                }
            return Futures.immediateFuture(updatedMediaItems)
        }

        private fun getMediaItemFromSearchQuery(query: String): MediaItem {
            // Only accept query with pattern "play [Title]" or "[Title]"
            // Where [Title]: must be exactly matched
            // If no media with exact name found, play a random media instead
            val mediaTitle =
                if (query.startsWith("play ", ignoreCase = true)) {
                    query.drop(5)
                } else {
                    query
                }

            return MediaItemTree.getItemFromTitle(mediaTitle) ?: MediaItemTree.getRandomItem()
        }*/
    }



}