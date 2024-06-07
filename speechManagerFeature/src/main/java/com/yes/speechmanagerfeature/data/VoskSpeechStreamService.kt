package com.yes.speechmanagerfeature.data

import android.content.Context
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import android.media.AudioTrack
import android.os.Environment
import android.os.Handler
import android.os.Looper
import org.jtransforms.fft.DoubleFFT_1D
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechStreamService
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import kotlin.experimental.and
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class VoskSpeechStreamService(
    recognizer: Recognizer, inputStream: InputStream, sampleRate: Float, context: Context
) : SpeechStreamService(recognizer, inputStream, sampleRate) {
    val audioTrackchannelConfig = AudioFormat.CHANNEL_OUT_MONO
    val audioRecordchannelConfig = AudioFormat.CHANNEL_OUT_STEREO
    val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    var recognizer: Recognizer = recognizer
    var inputStream: InputStream = inputStream
    var sampleRate = sampleRate.toInt()
    val BUFFER_SIZE_SECONDS = 0.2f

    //  var bufferSize = (this.sampleRate * BUFFER_SIZE_SECONDS * 2).roundToInt()
    val audioRecordBufferSize = AudioRecord.getMinBufferSize(
        this.sampleRate,
        audioRecordchannelConfig,
        audioFormat
    )
    //val audioRecordBufferSize=16000*2
    var recognizerThread: Thread? = null
    val mainHandler = Handler(Looper.getMainLooper())

    ///////////////////

    private val audioTrackbufferSize = AudioTrack.getMinBufferSize(
        sampleRate.toInt(),
        audioTrackchannelConfig,
        audioFormat
    )

    private val audioTrack = AudioTrack(
        AudioManager.STREAM_MUSIC,
        this.sampleRate,
        audioTrackchannelConfig,
        audioFormat,
        audioTrackbufferSize,
        AudioTrack.MODE_STREAM
    )

    //////////////////////
    override fun start(listener: RecognitionListener?): Boolean {
        if (null != recognizerThread) return false
        recognizerThread = RecognizerThread(listener!!)
        (recognizerThread as RecognizerThread).start()
        return true
    }

    override fun start(listener: RecognitionListener?, timeout: Int): Boolean {
        if (null != recognizerThread) return false
        recognizerThread = RecognizerThread(listener!!, timeout)
        recognizerThread?.start()
        return true
    }

    override fun stop(): Boolean {
        if (null == recognizerThread) return false
        try {
            recognizerThread!!.interrupt()
            recognizerThread!!.join()
        } catch (e: InterruptedException) {
            // Restore the interrupted status.
            Thread.currentThread().interrupt()
        }
        recognizerThread = null
        return true
    }

    private inner class RecognizerThread(
        var listener: RecognitionListener,
        timeout: Int = Companion.NO_TIMEOUT
    ) :
        Thread() {
        private var remainingSamples: Int
        private val timeoutSamples: Int

        init {
            if (timeout != Companion.NO_TIMEOUT) timeoutSamples =
                timeout * sampleRate / 1000 else timeoutSamples = Companion.NO_TIMEOUT
            remainingSamples = timeoutSamples
        }

        fun convertDoubleArrayToByteArray(doubleArray: DoubleArray): ByteArray {
            /*  val byteBuffer = ByteBuffer.allocate(doubleArray.size * java.lang.Double.BYTES)
              val doubleBuffer = byteBuffer.asDoubleBuffer()
              doubleBuffer.put(doubleArray)
              return byteBuffer.array()*/

            ////////////////
            val shortArray = ShortArray(doubleArray.size) {
                (doubleArray[it] * Short.MAX_VALUE).toInt().toShort()
            }
            val byteArray = ByteArray(shortArray.size * 2)
            for (i in shortArray.indices) {
                byteArray[i * 2] = (shortArray[i] and 0x00ff).toByte()
                byteArray[i * 2 + 1] = (shortArray[i].toInt() shr 8).toByte()
            }
            return byteArray
        }

        fun byteArrayToShortArray(byteArray: ByteArray): ShortArray {
            val shortArray = ShortArray(byteArray.size / 2)

            for (i in shortArray.indices) {
                val byte1 = byteArray[i * 2].toInt() and 0xFF
                val byte2 = byteArray[i * 2 + 1].toInt() and 0xFF
                shortArray[i] = (byte2 shl 8 or byte1).toShort()
            }

            return shortArray
        }

        fun normalize(input: ByteArray): ByteArray {
            val output = byteArrayToShortArray(input)
            val maxSample = output.maxOf {
                abs(it.toInt())
            }
            val normalFactor = Short.MAX_VALUE.toDouble() / maxSample
            val normalShorts = output.map {
                (it * normalFactor).toInt().toShort()
            }.toShortArray()
            val bytes = ByteArray(normalShorts.size * 2)
            for (i in normalShorts.indices) {
                bytes[i * 2] = (normalShorts[i] and 0x00ff).toByte()
                bytes[i * 2 + 1] = (normalShorts[i].toInt() shr 8).toByte()
            }
            return bytes
        }

        fun normalizeAudio(audioData: ByteArray, targetAmplitude: Double): ByteArray {
            val maxAmplitude = audioData.maxOf { it.toDouble() }
            val scaleFactor = targetAmplitude / maxAmplitude

            val normalizedData = ByteArray(audioData.size)
            for (i in audioData.indices) {
                val sample = (audioData[i].toDouble() * scaleFactor).toInt()
                normalizedData[i] = sample.toByte()
            }

            return normalizedData
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

        fun getAmplitudesAndPhases(
            channel: DoubleArray,
            amplitudes: DoubleArray,
            phases: DoubleArray
        ) {
            val fft = DoubleFFT_1D(channel.size.toLong())
            fft.realForward(channel)
            for (i in 0 until channel.size step 2) {
                val re = channel[i]
                val im = channel[i + 1]
                amplitudes[i / 2] = sqrt(re * re + im * im)
                phases[i / 2] = atan2(im, re)
            }
        }
        fun getOutputFromAmplitudesAndPhases(
            channel: DoubleArray,
            amplitudes: DoubleArray,
            phases: DoubleArray
        ): ByteArray {
            val fft = DoubleFFT_1D(channel.size.toLong())
            for (i in amplitudes.indices) {
                channel[i * 2] = amplitudes[i] * cos(phases[i])
                channel[i * 2 + 1] = amplitudes[i] * sin(phases[i])
            }
            fft.realInverse(channel, true)
            val output = ByteArray(channel.size)
            for (i in channel.indices) {
                output[i] = channel[i].toInt().toByte()
            }
            return output
        }

        override fun run() {
            val wavRecorder=WavRecorder(
                sampleRate,
                1,
                16,
                "fileName.wav"
            )
           /* val mediaDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
            val outputFile = File(mediaDir, "fileName.wav")

            if (!mediaDir.exists()) {
                mediaDir.mkdirs()
            }

            val headerBytes = getHeaderBytes(
                sampleRate,
                1,
                16,
                448324
            )
            val outputStream = FileOutputStream(outputFile)
            outputStream.write(headerBytes)*/

            val channel = ByteArray(audioRecordBufferSize)
            val rightChannel = DoubleArray(audioRecordBufferSize/2)
            val leftChannel = DoubleArray(audioRecordBufferSize/2)
            val monoChannel = DoubleArray(audioRecordBufferSize)

            audioTrack.play()
            while (!interrupted()
                && (timeoutSamples == Companion.NO_TIMEOUT || remainingSamples > 0)
            ) {
                try {
                    val nread = inputStream.read(channel, 0, audioRecordBufferSize)
                    ///////////////////////////////////////
                    getMonoChannels(channel, rightChannel, leftChannel)
                    ////////////////////
                    val amplitudesLeft = DoubleArray(leftChannel.size / 2)
                    val phasesLeft = DoubleArray(leftChannel.size / 2)
                    val amplitudesRight = DoubleArray(rightChannel.size / 2)
                    val phasesRight = DoubleArray(rightChannel.size / 2)
                    getAmplitudesAndPhases(rightChannel, amplitudesRight, phasesRight)

                    getAmplitudesAndPhases(leftChannel, amplitudesLeft, phasesLeft)
                    /////////////
                   /*   for (i in amplitudesRight.indices) {
                          val delta=amplitudesLeft[i] - amplitudesRight[i]
                          amplitudesRight[i] =amplitudesRight[i]-delta
                       //   amplitudesLeft[i] = amplitudesLeft[i] - amplitudesRight[i]
                         // phasesLeft[i] = phasesLeft[i] - phasesRight[i]
                         /* if (amplitudesLeft[i] - amplitudesRight[i] < 0) {
                              amplitudesLeft[i] = 0.0
                              //  phases[i]=0.0
                          } else {
                              amplitudesLeft[i] =amplitudesLeft[i] - amplitudesRight[i]
                              //   phases[i] = phases[i] - phasesRight[i]
                          }*/

                      }*/

                  /*  for (i in 0 until leftChannel.size step 2) {

                        if (amplitudeSecondChannel > threshold) {
                            firstChannelData[i] = 0.0 // Действительная часть
                            firstChannelData[i + 1] = 0.0 // Мнимая часть
                        }
                    }*/
                    /////////////
                    //////////////////
                   /* val output = ByteArray(rightChannel.size)
                    for (i in rightChannel.indices) {
                        output[i] = rightChannel[i].toInt().toByte()
                    }*/

                    val output=getOutputFromAmplitudesAndPhases(
                        rightChannel,
                        amplitudesRight,
                        phasesRight
                    )


                    ////////normalize
                       normalizeAudio(output , Short.MAX_VALUE.toDouble() )
                    //   normalize(outputLeft)
                    //////////////////////////

                    ////////////////////////////////////
                    if (nread < 0) {
                        break
                    } else {
                        /////////////////////////
                        /*  for (i in 0 until nread step 4){
                              rightbuf[i/2]=buffer[i+2]
                              rightbuf[i/2+1]=buffer[i+3]

                              leftbuf[i/2]=buffer[i]
                              leftbuf[i/2+1]=buffer[i+1]
                          }*/

                        //  audioTrack.write(leftbuf, 0, rightbuf.size)
                        //   val byteArray = convertDoubleArrayToByteArray(leftChannel)
                       wavRecorder.write(output)
                      //  outputStream.write(output, 0, output.size)
                        // audioTrack.write(outputRight, 0, audioRecordBufferSize / 2)
                        //  audioTrack.write(buffer, 0, buffer.size)
                        /////////////////////////
                        val isSilence = recognizer.acceptWaveForm(output, output.size)
                        if (isSilence) {
                            val result = recognizer.result
                            mainHandler.post { listener.onResult(result) }
                        } else {
                            val partialResult = recognizer.partialResult
                            mainHandler.post { listener.onPartialResult(partialResult) }
                        }
                    }
                    if (timeoutSamples != Companion.NO_TIMEOUT) {
                        remainingSamples = remainingSamples - nread
                    }
                } catch (e: IOException) {
                    mainHandler.post { listener.onError(e) }
                }
            }
            wavRecorder.close()
           /* outputStream.flush()
            outputStream.close()*/

            // If we met timeout signal that speech ended
            if (timeoutSamples != Companion.NO_TIMEOUT && remainingSamples <= 0) {
                mainHandler.post { listener.onTimeout() }
            } else {
                val finalResult = recognizer!!.finalResult
                mainHandler.post { listener.onFinalResult(finalResult) }
            }
        }


    }


    fun getHeaderBytes(
        sampleRate: Int,
        numChannels: Int,
        bitsPerSample: Int,
        dataSize: Int
    ): ByteArray {
        val headerSize = 44
        val header = ByteArray(headerSize)

        // ChunkID - "RIFF"
        header[0] = 'R'.code.toByte()
        header[1] = 'I'.code.toByte()
        header[2] = 'F'.code.toByte()
        header[3] = 'F'.code.toByte()

        // ChunkSize - общий размер файла минус 8 байтов
        val chunkSize = dataSize + 36
        header[4] = (chunkSize and 0xFF).toByte()
        header[5] = (chunkSize shr 8 and 0xFF).toByte()
        header[6] = (chunkSize shr 16 and 0xFF).toByte()
        header[7] = (chunkSize shr 24 and 0xFF).toByte()

        // Format - "WAVE"
        header[8] = 'W'.code.toByte()
        header[9] = 'A'.code.toByte()
        header[10] = 'V'.code.toByte()
        header[11] = 'E'.code.toByte()

        // Subchunk1ID - "fmt "
        header[12] = 'f'.code.toByte()
        header[13] = 'm'.code.toByte()
        header[14] = 't'.code.toByte()
        header[15] = ' '.code.toByte()

        // Subchunk1Size - 16 for PCM
        header[16] = 16
        header[17] = 0
        header[18] = 0
        header[19] = 0

        // AudioFormat - PCM = 1
        header[20] = 1
        header[21] = 0

        // NumChannels
        header[22] = numChannels.toByte()
        header[23] = 0

        // SampleRate
        header[24] = (sampleRate and 0xFF).toByte()
        header[25] = (sampleRate shr 8 and 0xFF).toByte()
        header[26] = (sampleRate shr 16 and 0xFF).toByte()
        header[27] = (sampleRate shr 24 and 0xFF).toByte()

        // ByteRate
        val byteRate = sampleRate * numChannels * bitsPerSample / 8
        header[28] = (byteRate and 0xFF).toByte()
        header[29] = (byteRate shr 8 and 0xFF).toByte()
        header[30] = (byteRate shr 16 and 0xFF).toByte()
        header[31] = (byteRate shr 24 and 0xFF).toByte()

        // BlockAlign
        val blockAlign = (numChannels * bitsPerSample / 8).toShort()
        header[32] = blockAlign.toByte()
        header[33] = 0

        // BitsPerSample
        header[34] = bitsPerSample.toByte()
        header[35] = 0

        // Subchunk2ID - "data"
        header[36] = 'd'.code.toByte()
        header[37] = 'a'.code.toByte()
        header[38] = 't'.code.toByte()
        header[39] = 'a'.code.toByte()

        // Subchunk2Size - dataSize
        header[40] = (dataSize and 0xFF).toByte()
        header[41] = (dataSize shr 8 and 0xFF).toByte()
        header[42] = (dataSize shr 16 and 0xFF).toByte()
        header[43] = (dataSize shr 24 and 0xFF).toByte()

        return header
    }


    companion object {
        private const val NO_TIMEOUT = -1
    }
   // vosk.SetLogLevel(-1) disabled logs
}