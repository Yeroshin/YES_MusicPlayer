package com.yes.core.data.factory

import android.media.audiofx.Visualizer

class  VisualizerFactory() {
    fun createVisualizer(mediaSessionId:Int): Visualizer {
        return Visualizer(mediaSessionId)
    }
}