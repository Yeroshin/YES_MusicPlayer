package com.yes.player.presentation.ui


import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.media.MicrophoneInfo
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.yes.core.presentation.ui.BaseDependency
import com.yes.core.presentation.ui.BaseFragment
import com.yes.core.presentation.ui.UiState
import com.yes.player.databinding.PlayerBinding
import com.yes.player.presentation.contract.PlayerContract
import com.yes.player.presentation.model.PlayerStateUI
import com.yes.player.presentation.ui.tmp.VoskSpeechService
import com.yes.player.presentation.ui.tmp.YESSpeechRecognizer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.StorageService
import java.io.IOException


class PlayerScreen : BaseFragment() {

    interface DependencyResolver {
        fun resolvePlayerFragmentDependency(): BaseDependency
    }

    override val dependency by lazy {
        (requireActivity().application as DependencyResolver)
            .resolvePlayerFragmentDependency()
    }


    private val binder by lazy {
        binding as PlayerBinding
    }


    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding {
        return PlayerBinding.inflate(inflater, container, false)
    }


    override fun showEffect() {

    }
    private var model: Model? = null
    @RequiresApi(Build.VERSION_CODES.P)
    fun tmp(context: Context?){
        ////////////////////////////
        val audioManager = context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val volume_level: Int = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxVolume: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val volumeDb=audioManager.getStreamVolumeDb(
            AudioManager.STREAM_MUSIC,
            volume_level,
            AudioDeviceInfo.TYPE_BUILTIN_SPEAKER
        )
        val mics:List<MicrophoneInfo> = audioManager.microphones
        /////////////////////////////
    }
    @RequiresApi(Build.VERSION_CODES.P)
    private fun initModel(context: Context?) {
        StorageService.unpack(context, "model", "model",
            { model: Model ->
                this.model = model
                recognizeMicrophone()
                tmp(context)
            },
            { exception: IOException ->
                println("Failed to unpack the model" + exception.message)
            })
    }

    private var speechRecognizer: YESSpeechRecognizer? = null
    private var speechService: SpeechService? = null
    private val speechListener=object: RecognitionListener{
        override fun onPartialResult(hypothesis: String?) {
            println(hypothesis)
        }

        override fun onResult(hypothesis: String?) {
            println(hypothesis)
        }

        override fun onFinalResult(hypothesis: String?) {
            println(hypothesis)
        }

        override fun onError(exception: Exception?) {
            println(exception.toString())
        }

        override fun onTimeout() {
            println("onTimeout")
        }

    }
    private fun recognizeMicrophone() {

        if (speechService != null) {

            speechService?.stop()
            speechService = null
        } else {

            try {
                val rec = Recognizer(model, 16000.0f,"[\"привет\",  \"[unk]\"]")
                rec.setMaxAlternatives(10)
                rec.setPartialWords(true)
                speechService = VoskSpeechService(rec, 16000.0f)
                speechService?.startListening(speechListener)
            } catch (e: IOException) {
                println(e.message)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onResume() {
        super.onResume()
        ////////////////////////
       initModel(super.getContext())

        ////////////////////////
       /* val rec = YESAudioRecorder(
            onRecorded = {
                it?.let {
                    speechRecognizer?.start(
                        it
                    )
                }
            }
        )
        speechRecognizer = YESSpeechRecognizer(
            requireContext(),
            onPartialResults = { rec.start() }
        )
        binder.btnPlay.setOnClickListener {
            rec.start()
        }
        binder.btnRew.setOnClickListener {
            rec.stop()
        }*/
    }
////////////////////


    //////////////////////
    override fun setUpView() {

         binder.btnPlay.setOnClickListener {
             viewModel.setEvent(PlayerContract.Event.OnPlay)
         }
         binder.btnRew.setOnClickListener {
             viewModel.setEvent(PlayerContract.Event.OnSeekToPrevious)
         }
        binder.btnFwd.setOnClickListener {
            viewModel.setEvent(PlayerContract.Event.OnSeekToNext)
        }
        binder.seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    viewModel.setEvent(PlayerContract.Event.OnSeek(progress))
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        }
        )
    }


    override fun renderUiState(state: UiState) {
        state as PlayerContract.State
        when (state.playerState) {
            is PlayerContract.PlayerState.Success -> {
                dataLoaded(
                    state.info
                )
            }

            is PlayerContract.PlayerState.Idle -> {
                idleView()
            }

        }
    }

    private fun dataLoaded(playerState: PlayerStateUI?) {
        playerState?.let {
            if (playerState.stateBuffering) {
                showBuffering()
            } else {
                hideBuffering()
                playerState.playListName?.let {
                    binder.playListName.text = playerState.playListName
                }
                playerState.trackTitle?.let {
                    binder.trackTitle.text = playerState.trackTitle
                }
                playerState.durationCounter?.let {
                    binder.durationCounter.gravity = Gravity.CENTER_HORIZONTAL
                    binder.durationCounter.gravity = Gravity.CENTER_VERTICAL
                    binder.durationCounter.text = playerState.durationCounter
                }
                playerState.duration?.let { binder.duration.text = playerState.duration }
                playerState.progress?.let {
                    binder.seekBar.progress = playerState.progress
                }
                playerState.durationInt?.let {
                    binder.seekBar.max = playerState.durationInt
                }
                playerState.visualizerData?.let {
                    binder.visualizerView.setValue(
                        playerState.visualizerData
                    )
                }
                playerState.isPlaying?.let {
                    binder.btnPlay.isActivated = it
                }
            }

        }


    }

    private fun idleView() {

    }


    private fun showError(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private val animateTextJob = Job()
    private val animateTextCoroutineScope =
        CoroutineScope(lifecycleScope.coroutineContext + animateTextJob)

    private fun showBuffering() {

        binder.durationCounter.gravity = Gravity.LEFT
        binder.durationCounter.gravity = Gravity.CENTER_VERTICAL
        val initialText = resources.getString(com.yes.coreui.R.string.buffering)
        val dots = "..."
        val animationDuration = 500L // Длительность анимации в миллисекундах

        val animation = AlphaAnimation(1.0f, 1.0f).apply {
            duration = animationDuration / 2
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }

        animateTextCoroutineScope.launch {
            binder.durationCounter.startAnimation(animation)
            var dotCount = 0
            while (true) {
                val text = dots.substring(0, dotCount) + " ".repeat(dots.length - dotCount)
                binder.durationCounter.text = initialText + text
                binder.durationCounter.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(com.yes.coreui.R.dimen.font_size_small)
                )
                dotCount = (dotCount + 1) % (dots.length + 1)
                delay(animationDuration)
            }
        }
    }

    private fun hideBuffering() {
        animateTextJob.cancel()
    }

}
