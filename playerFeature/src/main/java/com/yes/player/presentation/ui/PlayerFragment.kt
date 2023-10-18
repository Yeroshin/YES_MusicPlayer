package com.yes.player.presentation.ui

import android.content.ComponentName
import android.media.browse.MediaBrowser
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast


import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.viewbinding.ViewBinding
import com.google.common.util.concurrent.ListenableFuture

import com.google.common.util.concurrent.MoreExecutors
import com.yes.player.databinding.PlayerBinding
import com.yes.player.presentation.MusicService


import java.util.UUID


class PlayerFragment : Fragment() {
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private val controller: MediaController?
        get() = if (controllerFuture.isDone) controllerFuture.get() else null


    override fun onStart() {
        super.onStart()
        initializeController()

    }
    private fun initializeController() {
        controllerFuture =
            MediaController.Builder(
                requireContext(),
                SessionToken(
                    requireContext(),
                    ComponentName(requireContext(),
                        MusicService::class.java
                    )
                )
            )
                .buildAsync()
        controllerFuture.addListener({ setController() }, MoreExecutors.directExecutor())
    }
    private fun setController() {
        val controller = this.controller ?: return
        controller.addListener(
            object : Player.Listener {
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    Toast.makeText(context, "MediaItemTransition", Toast.LENGTH_SHORT).show()
                }

                override fun onTracksChanged(tracks: Tracks) {
                    Toast.makeText(context, "TracksChanged", Toast.LENGTH_SHORT).show()
                }
                override fun onPlaybackStateChanged(playbackState: Int) {
                    // Обработка изменений состояния проигрывания
                    when (playbackState) {
                        Player.STATE_IDLE -> {
                            Toast.makeText(context, "idle", Toast.LENGTH_SHORT).show()
                        }
                        Player.STATE_BUFFERING -> {
                            Toast.makeText(context, "buffering", Toast.LENGTH_SHORT).show()
                        }
                        Player.STATE_READY -> {
                            Toast.makeText(context, "ready", Toast.LENGTH_SHORT).show()
                        }
                        Player.STATE_ENDED -> {
                            Toast.makeText(context, "ended", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
    private lateinit var binding: ViewBinding
    private val binder by lazy {
        binding as PlayerBinding
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlayerBinding.inflate(inflater, container, false)
        //  super.onCreateView(inflater, container, savedInstanceState)
        binder.btnPlay.setOnClickListener {
            loadItem()
        }
        binder.btnRew.setOnClickListener {
            seekToPrevious()
        }
        binder.btnFwd.setOnClickListener {
            next()
        }
        //////////////////////////

        //////////////////////////
        return binder.root
    }
    private fun seekToPrevious(){
        controller?.seekToPreviousMediaItem()
    }
    fun next(){
        controller?.seekToNext()
        controller?.play()
    }

    private fun loadItem() {
      /*  val mediaItem = MediaItem.Builder()
            .setUri("https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3")
           // .setMediaMetadata(metadata)
            .build()
        controller?.setMediaItem(mediaItem)*/
        //controller.prepare()
        controller?.play()
    }



    /*
     private lateinit var browserFuture: ListenableFuture<MediaBrowser>
     private val browser: MediaBrowser?
         get() = if (browserFuture.isDone && !browserFuture.isCancelled) browserFuture.get() else null
     private lateinit var controllerFuture: ListenableFuture<MediaController>
     private val controller: MediaController?
         get() = if (controllerFuture.isDone) controllerFuture.get() else null
     override fun onStart() {
         super.onStart()
         initializeController()
         initializeBrowser()
     }
     private fun initializeBrowser() {
         browserFuture =
             MediaBrowser.Builder(
                 requireActivity(),
                 SessionToken(requireActivity(), ComponentName(requireActivity(), MusicService::class.java))
             )
                 .buildAsync()
         browserFuture.addListener({ pushRoot() }, ContextCompat.getMainExecutor(requireActivity()))
     }
     private fun pushRoot() {
         // browser can be initialized many times
         // only push root at the first initialization
         val browser = this.browser ?: return
         val rootFuture = browser.getLibraryRoot(/* params= */ null)
         rootFuture.addListener(
             {
                 val result: LibraryResult<MediaItem> = rootFuture.get()!!
                 val root: MediaItem = result.value!!
              //   pushPathStack(root)
             },
             ContextCompat.getMainExecutor(requireContext())
         )
     }
     private fun initializeController() {
         controllerFuture =
             MediaController.Builder(
                 requireActivity(),
                 SessionToken(requireActivity(), ComponentName(requireActivity(), MusicService::class.java))
             )
                 .buildAsync()
         controllerFuture.addListener({ setController() }, MoreExecutors.directExecutor())
     }

     private fun setController() {
         val controller = this.controller ?: return

        /* playerView.player = controller

         updateCurrentPlaylistUI()
         updateMediaMetadataUI(controller.mediaMetadata)
         playerView.setShowSubtitleButton(controller.currentTracks.isTypeSupported(TRACK_TYPE_TEXT))
 */
         controller.addListener(
             object : Player.Listener {
                 override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                   //  updateMediaMetadataUI(mediaItem?.mediaMetadata ?: MediaMetadata.EMPTY)
                 }

                 override fun onTracksChanged(tracks: Tracks) {
                    // playerView.setShowSubtitleButton(tracks.isTypeSupported(TRACK_TYPE_TEXT))
                 }
                 override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
                     super.onMediaMetadataChanged(mediaMetadata)

                 }

                 override fun onIsPlayingChanged(isPlaying: Boolean) {
                     super.onIsPlayingChanged(isPlaying)

                 }

                 override fun onPlaybackStateChanged(playbackState: Int) {

                     super.onPlaybackStateChanged(playbackState)
                 }

                 override fun onPlayerError(error: PlaybackException) {
                     super.onPlayerError(error)
                 }

                 override fun onPlayerErrorChanged(error: PlaybackException?) {
                     super.onPlayerErrorChanged(error)

                 }
             }
         )
     }
     var playlistId = UUID.randomUUID().toString()
   //  private lateinit var mediaBrowserConnectionCallback:MediaBrowserConnectionCallback
    // private lateinit var mediaBrowser : MediaBrowserCompat
   private fun displayFolder() {
       val browser = this.browser ?: return
       val id: String = "intent.getStringExtra(MEDIA_ITEM_ID_KEY)!!"
       val mediaItemFuture = browser.getItem(id)
       val childrenFuture =
           browser.getChildren(id, /* page= */ 0, /* pageSize= */ Int.MAX_VALUE, /* params= */ null)
       mediaItemFuture.addListener(
           {
            //   val title: TextView = findViewById(R.id.folder_description)
               val result = mediaItemFuture.get()!!
             //  title.text = result.value!!.mediaMetadata.title
           },
           ContextCompat.getMainExecutor(requireContext())
       )
       childrenFuture.addListener(
           {
               val result = childrenFuture.get()!!
               val children = result.value!!

              /* subItemMediaList.clear()
               subItemMediaList.addAll(children)
               mediaListAdapter.notifyDataSetChanged()*/
           },
           ContextCompat.getMainExecutor(requireContext())
       )
   }
     private fun loadItem() {
      /* controller!!.setMediaItem(MediaItem.fromUri(
           "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3"
       ))*/

         displayFolder()
     /*  val media = MediaItem.Builder().setMediaId(
           "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3"
       ).build()*/
       val media = MediaItem.Builder().setUri(
           "https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3"
       ).build()
       controller!!.setMediaItem(media)
       controller!!.prepare()
       controller!!.play()

        /* val mediaId = "unique_media_id" // Уникальный идентификатор элемента плейлиста
         val title = "Название трека"
         val artist = "Исполнитель"
         val album = "Альбом"
         val duration = 300000L// Длительность трека в миллисекундах
         val filePath = "/путь/к/файлу.mp3" // Путь к файлу

 // Создаем MediaDescriptionCompat для элемента плейлиста
         val mediaDescription = MediaDescriptionCompat.Builder()
             .setMediaId(mediaId)
             .setTitle(title)
             .setSubtitle(artist)
             .setDescription(album)
             .setMediaUri(Uri.fromFile(File(filePath)))
             .build()

 // Создаем MediaMetadataCompat для элемента плейлиста
         val mediaMetadata = MediaMetadataCompat.Builder()
             .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, mediaDescription.mediaId.toString())
             .putString(MediaMetadataCompat.METADATA_KEY_TITLE, mediaDescription.title.toString())
             .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, mediaDescription.subtitle.toString())
             .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, mediaDescription.description.toString())
             .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration)
             .build()

 // Создаем MediaSessionCompat.QueueItem для элемента плейлиста
         val mediaItem = MediaSessionCompat.QueueItem(mediaDescription, mediaMetadata.description.mediaId.hashCode().toLong())
        // mediaBrowser.connect()*/
      //  val mediaItem = MediaBrowserCompat.MediaItem.fromUri(getString(R.string.media_url_mp3))
      /*   val mediaController = MediaController(requireContext(), mediaBrowser.sessionToken)

     //   mediaController.addQueueItem(mediaItem)
         val mediaDescription = MediaDescriptionCompat.Builder()
             .setMediaId("wake_up_01")
             .setTitle("Intro - The Way Of Waking Up (feat. Alan Watts)")
             .setMediaUri(Uri.parse("
             https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/01_-_Intro_-_The_Way_Of_Waking_Up_feat_Alan_Watts.mp3
             "))
             .build()
         mediaController.addQueueItem(mediaDescription)
         mediaController.transportControls.play();*/

     }*/
    /*  private var subscriptionCallback: MediaBrowserCompat.SubscriptionCallback =
          object : MediaBrowserCompat.SubscriptionCallback() {
              override fun onChildrenLoaded(
                  parentId: String,
                  children: List<MediaBrowserCompat.MediaItem>
              ) {
                  val a=parentId
                  val b=children
                  // Обработка изменений в плейлисте
              }
          }*/
    // private lateinit var mediaController: MediaControllerCompat
    /* private inner class MediaControllerCallback : MediaControllerCompat.Callback() {

         override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
             //  playbackState.postValue(state ?: EMPTY_PLAYBACK_STATE)
         }

         override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
             // When ExoPlayer stops we will receive a callback with "empty" metadata. This is a
             // metadata object which has been instantiated with default values. The default value
             // for media ID is null so we assume that if this value is null we are not playing
             // anything.
             /* nowPlaying.postValue(
                 if (metadata?.id == null) {
                     NOTHING_PLAYING
                 } else {
                     metadata
                 }
             )*/
         }
     }
     private inner class MediaBrowserConnectionCallback(private val context: Context) :
         MediaBrowserCompat.ConnectionCallback() {
         override fun onConnected() {
             // Get a MediaController for the MediaSession.
             mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                 registerCallback(MediaControllerCallback())
             }
             mediaBrowser.subscribe(playlistId, subscriptionCallback);
             //  isConnected.postValue(true)
         }

         override fun onConnectionSuspended() {
             //  isConnected.postValue(false)
         }

         override fun onConnectionFailed() {
             //  isConnected.postValue(false)
         }
     }*/

}