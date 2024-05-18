package com.yes.speechmanagerfeature.data

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.media.audiofx.NoiseSuppressor
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import java.io.IOException
import kotlin.experimental.and
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.sqrt


@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("MissingPermission")
class VoskSpeechService(
    private val context: Context,
    private val recognizer: Recognizer,
    private val sampleRate: Float,
    private val onGetSoundLevel: (level: Double) -> Unit
) : SpeechService(
    recognizer,
    sampleRate
) {
    private val audioRecordChannelConfigMono = AudioFormat.CHANNEL_IN_MONO
    private val audioRecordChannelConfigStereo = AudioFormat.CHANNEL_IN_STEREO
    private val audioEncodingFormat = AudioFormat.ENCODING_PCM_16BIT
    private val timeSec = 2

     private val audioRecordBufferSize = AudioRecord.getMinBufferSize(
         sampleRate.toInt(),
         audioRecordChannelConfigStereo,
         audioEncodingFormat
     )
  //  private val audioRecordBufferSize = (sampleRate * audioEncodingFormat * timeSec).toInt()
    private val soundLength=(sampleRate * audioEncodingFormat * timeSec).toInt()
    private val recorder: AudioRecord = AudioRecord(
        MediaRecorder.AudioSource.MIC,
        sampleRate.toInt(),
        audioRecordChannelConfigStereo,
        audioEncodingFormat,
        audioRecordBufferSize
    )






    private val noiseSuppressor: NoiseSuppressor? = NoiseSuppressor.create(
        recorder.audioSessionId
    )
  // private val noiseSuppressor: NoiseSuppressor?=null

    private var recognizerThread: RecognizerThread? = null

    private val mainHandler = Handler(Looper.getMainLooper())

    init {

        if (recorder.state == AudioRecord.STATE_UNINITIALIZED) {
            recorder.release()
            throw IOException("Failed to initialize recorder. Microphone might be already in use.")
        }
    }

    override fun startListening(listener: RecognitionListener): Boolean {
        if (recognizerThread != null) return false

        recognizerThread = RecognizerThread(listener)
        recognizerThread?.start()
        return true
    }

    override fun startListening(listener: RecognitionListener, timeout: Int): Boolean {
        if (recognizerThread != null) return false

        recognizerThread = RecognizerThread(listener, timeout)
        recognizerThread?.start()
        return true
    }

    private fun stopRecognizerThread(): Boolean {
        if (recognizerThread == null) return false

        try {
            recognizerThread?.interrupt()
            recognizerThread?.join()
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        recognizerThread = null
        return true
    }

    override fun stop(): Boolean {
        return stopRecognizerThread()
    }

    override fun cancel(): Boolean {
        recognizerThread?.setPause(true)
        return stopRecognizerThread()
    }

    override fun shutdown() {
        recorder.release()
    }

    override fun setPause(paused: Boolean) {
        recognizerThread?.setPause(paused)
    }

    override fun reset() {
        recognizerThread?.reset()
    }
    var recording:Boolean=true
    fun stopRecord(){
        recording=false
        wavRecorderCamcorder.close()
        wavRecorderVoice.close()
    }
    val wavRecorderCamcorder = WavRecorder(
        sampleRate.toInt(),
        1,
        16,
        "soundTop.wav"
    )
    val wavRecorderVoice = WavRecorder(
        sampleRate.toInt(),
        1,
        16,
        "soundVoice.wav"
    )

    private inner class RecognizerThread(
        private val listener: RecognitionListener,
        private val timeout: Int = -1
    ) : Thread() {

        private var remainingSamples = timeout * sampleRate.toInt() / 1000
        private var paused = false
        private var reset = false


        override fun run() {
            noiseSuppressor?.let {
                if (!noiseSuppressor.enabled) {
                    noiseSuppressor.enabled = true
                }
            }



            recorder.startRecording()
            if (recorder.recordingState == AudioRecord.RECORDSTATE_STOPPED) {
                recorder.stop()
                val ioe =
                    IOException("Failed to start recording. Microphone might be already in use.")
                mainHandler.post { listener.onError(ioe) }
            }


            val recordStereo = ShortArray(audioRecordBufferSize)
            val recordLeft =  ByteArray(audioRecordBufferSize)
            val recordRight =  ByteArray(audioRecordBufferSize)
            var averageSoundLevel=0.0
            var soundLevelsCounted=0
            var prevSoundLevelLeft=0.0
            var prevSoundLevelRight=0
            var counter=0
            while (!isInterrupted && (timeout == -1 || remainingSamples > 0)) {
                val nread = recorder.read(recordStereo, 0, recordStereo.size)
////////////////////////////////////////
                getMonoChannels(recordStereo, recordRight , recordLeft)
                if (recording){
                    wavRecorderCamcorder.write(
                        recordLeft
                    )
                    wavRecorderVoice.write(
                        recordRight
                    )
                }

                ////soundlevel/////
                val soundLevelLeft = calculateVolumeInDecibels(recordLeft)
                val soundLevelRight = calculateVolumeInDecibels(recordRight)
                if (soundLevelsCounted<soundLength){
                    soundLevelsCounted+=nread/2
                    counter++
                    averageSoundLevel += soundLevelLeft
                }else{
                    soundLevelsCounted=0
                    val lev=averageSoundLevel/counter
                    averageSoundLevel=0.0
                    counter=0
                    mainHandler.post { listener.onResult("sound:$lev") }

                }
              //  onGetSoundLevel(soundLevelRight.toInt())
            //    mainHandler.post { listener.onResult("sound:$soundLevelRight") }
                println(
                    "soundLevelLeft:$soundLevelLeft"
                )
                println(
                    "soundLevelRight:$soundLevelRight"
                )


                //////////////////////////////////////////////
                if (paused) continue

                if (reset) {
                    recognizer.reset()
                    reset = false
                }

                if (nread < 0) throw RuntimeException("error reading audio buffer")

                if (recognizer.acceptWaveForm(recordRight, nread)) {
                    val result = recognizer.result
                    mainHandler.post { listener.onResult(result) }
                } else {
                    val partialResult = recognizer.partialResult
                    mainHandler.post { listener.onPartialResult(partialResult) }
                }

                if (timeout != -1) {
                    remainingSamples -= nread
                }
            }
          //  wavRecorderCamcorder.close()
          //  wavRecorderVoice.close()
            recorder.stop()
            if (!paused) {
                if (timeout != -1 && remainingSamples <= 0) {
                    mainHandler.post { listener.onTimeout() }
                } else {
                    val finalResult = recognizer.finalResult
                    mainHandler.post { listener.onFinalResult(finalResult) }
                }
            }
        }

        fun setPause(paused: Boolean) {
            this.paused = paused
        }

        fun reset() {
            reset = true
        }
    }

   /* private fun shortArrayToByteArray(shortArray: ShortArray): ByteArray {
        val byteArray = ByteArray(shortArray.size * 2)
        shortArray.forEachIndexed { index, element ->
            val byteIndex = index * 2
            byteArray[byteIndex] = (element.toInt() shr 8).toByte()
            byteArray[byteIndex + 1] = (element.toInt() and 0xFF).toByte()
        }
        return byteArray
    }*/
    fun convertShortArrayToByteArray(shortArray: ShortArray): ByteArray {
        val byteArray = ByteArray(shortArray.size * 2)
        for (i in shortArray.indices) {
            byteArray[i * 2] = (shortArray[i] and 0xFF).toByte()
            byteArray[i * 2 + 1] = (shortArray[i].toInt() ushr 8).toByte() }
        return byteArray
    }

    fun readSoundLevel(buffer: ShortArray): Double {

        var sum = 0.0
        for (short in buffer) {
            sum += short * short
        }
        if (sum == 0.0) {
            return 0.0
        }
        val amplitude = sum / buffer.size
        val amplitudeDb = 20 * log10(amplitude)
        return amplitudeDb
    }

    /*  fun calculateVolumeInDecibels(record: ShortArray): Double {
          var sumOfSquares = 0.0
          for (sample in record) {
              sumOfSquares += sample.toDouble() * sample.toDouble()
          }
          val meanSquare = if (record.isNotEmpty()) sumOfSquares / record.size else 0.0
          val rootMeanSquare = Math.sqrt(meanSquare)

          // Амплитуда референсного уровня (обычно 1 для нормализации)
          val referenceAmplitude = 1.0

          // Формула для преобразования амплитуды в децибелы
          return 20 * log10(rootMeanSquare / referenceAmplitude)
      }*/
    fun getVolumeInDecibels(record: ShortArray): Double {
        val REFERENCE = 0.00002
        var average = 0.0
        var bufferSize = record.size
        for (s in record) {
            if (s > 0) {
                average += abs(s.toInt()).toDouble()
            } else {
                bufferSize--
            }
        }
        val x = average / record.size
        val pressure =
            x / 51805.5336//the value 51805.5336 can be derived from asuming that x=32767=0.6325 Pa and x=1 = 0.00002 Pa (the reference value)
        val db = (20 * log10(pressure / REFERENCE))
        return db
    }

    fun calculateVolumeInDecibels(record: ShortArray): Double {
        var sumOfSquares = 0.0
        for (sample in record) {
            sumOfSquares += sample * sample
        }
        val meanSquare = if (record.isNotEmpty()) sumOfSquares / record.size else 0.0
        val rootMeanSquare = sqrt(meanSquare)

        // Максимально возможная амплитуда для 16-битного аудио
        val maxAmplitude = Short.MAX_VALUE//32767.0

        // Формула для преобразования амплитуды в децибелы
        return 20 * log10(rootMeanSquare / maxAmplitude)
    }
    fun getMonoChannels(
        channel: ByteArray,
        leftChannel: DoubleArray,
        rightChannel: DoubleArray
    ) {
        for (i in channel.indices step 4) {
            leftChannel[i / 2] = channel[i + 2].toDouble()
            leftChannel[i / 2 + 1] = channel[i + 3].toDouble()

            rightChannel[i / 2] = channel[i].toDouble()
            rightChannel[i / 2 + 1] = channel[i + 1].toDouble()
        }
    }
    fun getMonoChannels(
        stereoChannel: ShortArray,
        leftChannel: ByteArray,
        rightChannel: ByteArray
    ) {
        if (stereoChannel.size > leftChannel.size * 2 || stereoChannel.size > rightChannel.size * 2) {
            throw IllegalArgumentException("Размеры массивов leftChannel и rightChannel должны быть в два раза больше размера массива stereoChannel.")
        }

        for (i in stereoChannel.indices step 2) {
            // Преобразование short в два byte
            val leftSample = stereoChannel[i]
            leftChannel[i] = (leftSample and 0xFF).toByte()
            leftChannel[i + 1] = ((leftSample.toInt() shr 8) and 0xFF).toByte()

            val rightSample = stereoChannel[i + 1]
            rightChannel[i] = (rightSample and 0xFF).toByte()
            rightChannel[i + 1] = ((rightSample.toInt() shr 8) and 0xFF).toByte()
        }
    }
    fun calculateVolumeInDecibels(audioBytes: ByteArray): Double {
        var sumOfSquares = 0.0
        for (i in audioBytes.indices step 2) {
            val sample = (audioBytes[i].toInt() and 0xFF) or (audioBytes[i + 1].toInt() shl 8)
            sumOfSquares += (sample * sample).toDouble()
        }
        val meanSquare = if (audioBytes.isNotEmpty()) sumOfSquares / (audioBytes.size / 2) else 0.0
        val rootMeanSquare = sqrt(meanSquare)

        // Максимально возможная амплитуда для 16-битного аудио
        val maxAmplitude = Short.MAX_VALUE.toDouble()

        // Формула для преобразования амплитуды в децибелы
        return 20 * log10(rootMeanSquare / maxAmplitude)
    }
}
