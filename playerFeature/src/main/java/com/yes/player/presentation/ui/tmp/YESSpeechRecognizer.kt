package com.yes.player.presentation.ui.tmp

import android.content.Context
import android.content.Intent
import android.media.AudioFormat
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import java.io.File

class YESSpeechRecognizer(context: Context) {
    private var speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

    init {
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
    }

    fun start(pfd: ParcelFileDescriptor) {
       /* val pfd = ParcelFileDescriptor.open(
            file,
            ParcelFileDescriptor.MODE_READ_ONLY
        )*/
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_AUDIO_SOURCE, pfd)
        intent.putExtra(
            RecognizerIntent.EXTRA_AUDIO_SOURCE_CHANNEL_COUNT,
            AudioFormat.CHANNEL_IN_DEFAULT
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_AUDIO_SOURCE_ENCODING,
            AudioFormat.ENCODING_PCM_16BIT
        )
        intent.putExtra(RecognizerIntent.EXTRA_AUDIO_SOURCE_SAMPLING_RATE, 16000)
        speechRecognizer.startListening(intent)
    }
}