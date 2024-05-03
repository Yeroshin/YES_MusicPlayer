package com.yes.player.presentation.ui.tmp

import java.io.File

interface AudioRecorder {
    fun start(outputFile: File)
    fun stop()
}