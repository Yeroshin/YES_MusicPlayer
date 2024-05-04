package com.yes.player.presentation.ui.tmp

import android.content.Context
import android.content.Intent
import android.media.AudioFormat
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.SpeechRecognizer.isRecognitionAvailable
import androidx.annotation.RequiresApi
import java.io.File

class YESSpeechRecognizer(
    private val context: Context,
    onPartialResults:()->Unit
) {
  //  private val language="en-EN"
    private val language="ru-RU"
  // private val speechRecognizer = SpeechRecognizer.createOnDeviceSpeechRecognizer(context).apply {
    private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
        setRecognitionListener(
            object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                 //   println("onReadyForSpeech")
                }

                override fun onBeginningOfSpeech() {
                    println("onBeginningOfSpeech")
                }

                override fun onRmsChanged(rmsdB: Float) {
                  //  println("onRmsChanged")
                }

                override fun onBufferReceived(buffer: ByteArray?) {
                    println("onBufferReceived")
                }

                override fun onEndOfSpeech() {
                 //   println("onEndOfSpeech")
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
                   // println("onPartialResults")
                    val data: ArrayList<String>? =
                        partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    println(data)
                    data?.let {
                        if (data.isEmpty()){
                            t()
                        }
                    }

                 //   onPartialResults()
                }

                override fun onSegmentResults(segmentResults: Bundle) {
                    super.onSegmentResults(segmentResults)
                    println("onSegmentResults")
                }

                override fun onEndOfSegmentedSession() {
                    super.onEndOfSegmentedSession()
                    println("onEndOfSegmentedSession")
                }

                override fun onLanguageDetection(results: Bundle) {
                    super.onLanguageDetection(results)
                    println("onLanguageDetection")
                }

                override fun onEvent(eventType: Int, params: Bundle?) {
                    println(" onEvent")
                }
            }
        )
    }
    fun t(){
        val t=isRecognitionAvailable (context)
    }
    fun start(pfd: ParcelFileDescriptor) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
            putExtra(RecognizerIntent.EXTRA_AUDIO_SOURCE, pfd)
            putExtra(
                RecognizerIntent.EXTRA_AUDIO_SOURCE_CHANNEL_COUNT,
                AudioFormat.CHANNEL_IN_DEFAULT
            )
            putExtra(
                RecognizerIntent.EXTRA_AUDIO_SOURCE_ENCODING,
                AudioFormat.ENCODING_PCM_16BIT
            )
            putExtra(RecognizerIntent.EXTRA_AUDIO_SOURCE_SAMPLING_RATE, 16000)
          // putExtra(RecognizerIntent.FORMATTING_OPTIMIZE_LATENCY, true)

        }
        speechRecognizer.cancel()
         speechRecognizer.startListening(intent)
    }
}