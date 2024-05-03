package com.yes.player.presentation.ui


import android.content.Intent
import android.media.AudioFormat
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.yes.core.presentation.ui.BaseDependency
import com.yes.core.presentation.ui.BaseFragment
import com.yes.core.presentation.ui.UiState
import com.yes.player.databinding.PlayerBinding
import com.yes.player.presentation.contract.PlayerContract
import com.yes.player.presentation.model.PlayerStateUI
import com.yes.player.presentation.ui.tmp.AndroidAudioRecorder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException


class PlayerScreen :  BaseFragment() {

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
        return  PlayerBinding.inflate(inflater, container, false)
    }



    override fun showEffect() {

    }
    var speechRecognizer:SpeechRecognizer?=null
    var rec:AndroidAudioRecorder?= null
    val filePath="audio_record.pcm"
    override fun onResume() {
        super.onResume()
        /////////////////////////
        val sampleRate=44100
        val channelConfig=AudioFormat.CHANNEL_IN_MONO
        val audioFormat=AudioFormat.ENCODING_PCM_16BIT
      //  val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
        val bufferSize =441000


      //

      //  recorder.startRecording()

/////////////////////////////

        ///////////////
        val c=context
         speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)



        speechRecognizer?.setRecognitionListener(
            object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    println("onReadyForSpeech")
                }

                override fun onBeginningOfSpeech() {
                    println("onBeginningOfSpeech")
                }

                override fun onRmsChanged(rmsdB: Float) {
                    println("onReadyForSpeech")
                }

                override fun onBufferReceived(buffer: ByteArray?) {
                    println("onBufferReceived")
                }

                override fun onEndOfSpeech() {
                    println("onEndOfSpeech")
                }

                override fun onError(error: Int) {
                    println("onError$error")

                }

                override fun onResults(results: Bundle?) {
                    val data: ArrayList<String>? =
                        results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    println(data)
                  // speechRecognizer?.startListening(intent)
                }

                override fun onPartialResults(partialResults: Bundle?) {
                    println("onPartialResults")
                    val data: ArrayList<String>? =
                        partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    println(data)
                }

                override fun onEvent(eventType: Int, params: Bundle?) {
                    println(" onEvent")
                }


            }
        )

      //  speechRecognizer?.startListening(intent)
        binder.btnPlay.setOnClickListener {

            val file = File(context?.filesDir, filePath)
            file.createNewFile()
            context?.let {
                rec=AndroidAudioRecorder()
            }

            rec?.start(file)


        }
        binder.btnRew.setOnClickListener {
            rec?.stop()

            val f = File(requireContext().filesDir, filePath)
            val pfd = ParcelFileDescriptor.open(
                f,
                ParcelFileDescriptor.MODE_READ_ONLY
            )


            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            intent.putExtra(RecognizerIntent.EXTRA_AUDIO_SOURCE, pfd)
            intent.putExtra(RecognizerIntent.EXTRA_AUDIO_SOURCE_CHANNEL_COUNT, 1)
            intent.putExtra(RecognizerIntent.EXTRA_AUDIO_SOURCE_ENCODING, AudioFormat.ENCODING_PCM_16BIT)
            intent.putExtra(RecognizerIntent.EXTRA_AUDIO_SOURCE_SAMPLING_RATE, 16000)
           // intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech to text")
            speechRecognizer?.startListening(intent)
           // speechRecognizer?.stopListening()

        }
    }
////////////////////


    //////////////////////
    override fun setUpView() {
        ///////////////////////////////
      /*  speechRecognizer?.setRecognitionListener(
            object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    println("onReadyForSpeech")
                }

                override fun onBeginningOfSpeech() {
                    println("onReadyForSpeech")
                }

                override fun onRmsChanged(rmsdB: Float) {
                    println("onReadyForSpeech")
                }

                override fun onBufferReceived(buffer: ByteArray?) {
                    println("onReadyForSpeech")
                }

                override fun onEndOfSpeech() {
                    println("onReadyForSpeech")
                }

                override fun onError(error: Int) {
                    println("onReadyForSpeech")
                }

                override fun onResults(results: Bundle?) {
                    val data: ArrayList<String>? =
                        results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

                }

                override fun onPartialResults(partialResults: Bundle?) {
                    println("onReadyForSpeech")
                }

                override fun onEvent(eventType: Int, params: Bundle?) {
                    println("onReadyForSpeech")
                }


            }
        )

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        binder.btnPlay.setOnClickListener {
            speechRecognizer?.startListening(intent)

        }
        binder.btnRew.setOnClickListener {
            speechRecognizer?.stopListening()

        }*/
        ///////////////////////////////

       /* binder.btnPlay.setOnClickListener {
            viewModel.setEvent(PlayerContract.Event.OnPlay)
        }
        binder.btnRew.setOnClickListener {
            viewModel.setEvent(PlayerContract.Event.OnSeekToPrevious)
        }*/
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
                    binder.btnPlay.isActivated=it
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
