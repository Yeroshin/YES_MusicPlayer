package com.yes.speechmanagerfeature.data

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.media.MicrophoneInfo
import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.OptIn
import androidx.annotation.RequiresApi
import androidx.media3.common.util.UnstableApi
import org.json.JSONObject
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.StorageService
import java.io.IOException
import java.io.InputStream


@OptIn(UnstableApi::class)
@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.P)
class Speech(
    private val context: Context,
    private val onVoiceCommand:()->Unit,
    private val onGetVolume:(volume:Double?)->Unit
) {
    private var model: Model? = null

    private val speechListener = object : RecognitionListener {
        override fun onPartialResult(hypothesis: String?) {
            println(hypothesis)


        }

        override fun onResult(hypothesis: String?) {
            println(hypothesis)
            val prefix = "sound:"
            hypothesis?.let {
                if(hypothesis.startsWith(prefix)) {
                    onGetVolume(
                        hypothesis.removePrefix(prefix).toDoubleOrNull()
                    )
                }else if (checkGreetings(hypothesis)){
                    onVoiceCommand()
                }
            }
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
    fun checkGreetings(jsonString: String): Boolean {
        val jsonObject = JSONObject(jsonString)
        val alternativesArray = jsonObject.getJSONArray("alternatives")

        for (i in 0 until alternativesArray.length()) {
            val item = alternativesArray.getJSONObject(i)
            if (item.getString("text") == "привет") {
                return true
            }
        }
        return false
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
        /*  processor.setListener {byteBuffer->
            //  speechService?.setNoiseBuffer(byteBuffer)
          }*/
    }

    private fun recognizeMicrophone() {
        val rec = Recognizer(
            model,
            16000.0f,
            "[\"привет\",\"риве\",\"иве\",\"и\",\"ив\",\"ве\",\"в\",\"е\",\"э\",\"эт\",\"вет\",\"ет\",\"риве\",\"рив\",\"т\",\"спать\",\"спи\",  \"[unk]\"]"
        )
        rec.setMaxAlternatives(10)
        rec.setPartialWords(true)
        rec.setWords(true)
        ///////////////
        val speechService = VoskSpeechService(
            context,
            rec,
            16000.0f,
            {volume->onGetVolume(volume)}
        )
        speechService.startListening(speechListener)
        val timer = object: CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
              //  speechService.setPause(true)
                speechService.stopRecord()
              //  speechService.shutdown()
            }
        }
        timer.start()
        ////////////
       /*  val ais: InputStream = context.assets.open(
             "Recording_6.wav"
         )
           val speechService = VoskSpeechStreamService(
               rec,
               ais,
               16000.0f,
               context
           )
           speechService.start(speechListener)*/
        ///////////////
    }

    fun tmp() {
        ////////////////////////////
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val volume_level: Int = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxVolume: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val volumeDb = audioManager.getStreamVolumeDb(
            AudioManager.STREAM_MUSIC,
            volume_level,
            AudioDeviceInfo.TYPE_BUILTIN_SPEAKER
        )
        val mics: List<MicrophoneInfo> = audioManager.microphones
        /////////////////////////////
    }

}