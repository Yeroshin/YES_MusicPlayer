 package com.yes.core.presentation.ui.tmp

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.media.MicrophoneInfo
import android.os.Build
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.media3.common.util.UnstableApi
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.SpeechStreamService
import org.vosk.android.StorageService
import java.io.IOException
import java.io.InputStream


 @OptIn(UnstableApi::class)
@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.P)
class Speech(private val context: Context,private val processor: AudioProcessor) {
    private var model: Model? = null
   // private var speechService: VoskSpeechService? = null
   private var speechService: SpeechStreamService? = null
    private val speechListener=object: RecognitionListener {
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
    init {
        StorageService.unpack(
            context,
            "model",
            "model",
            { model: Model ->
                this.model = model
                   recognizeMicrophone()
                  tmp()
            },
            { exception: IOException ->
                println("Failed to unpack the model" + exception.message)
            }
        )
        processor.setListener {byteBuffer->
          //  speechService?.setNoiseBuffer(byteBuffer)
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
                val ais: InputStream = context.assets.open(
                    "Recording_15.wav"
                )
              //  speechService = VoskSpeechService(rec, 16000.0f)
                speechService = SpeechStreamService(rec,ais, 16000.0f)
              //  speechService?.startListening(speechListener)
                speechService!!.start(speechListener)
            } catch (e: IOException) {
                println(e.message)
            }
        }
    }

    fun tmp(){
        ////////////////////////////
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
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

}