package com.yes.player.presentation.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.abs

class VisualizerView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var width: Int = 0
    private var height: Int = 0
    private val paint = Paint()
    private val frequencies = mutableListOf<Float>()
    private val oldFrequencies = mutableListOf<Float>()
    private val maxValue = 1
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(h, w, oldh, oldw)
        width = w
        height = h
    }

    private var update: Boolean = false
    private val value= mutableListOf<Float>()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (update) {
            val interpolationFactor = 0.4f
            val rect = if (frequencies.size != 0) width / frequencies.size else 1
            val spacing = rect / 4
            val rectSide = rect - spacing
            val maxHeightCount = if (rect != 0) height / rect else 1
            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = rectSide.toFloat()
            for ((barIndex, value) in frequencies.withIndex()) {
                oldFrequencies[barIndex]=if (value > oldFrequencies[barIndex]) {
                    oldFrequencies[barIndex] + abs(interpolationFactor * (value - oldFrequencies[barIndex]))
                } else if (value < oldFrequencies[barIndex]) {
                     oldFrequencies[barIndex] - abs(interpolationFactor * (value - oldFrequencies[barIndex]))
                } else {
                    value
                }
                val barCount = ((oldFrequencies[barIndex] * maxHeightCount) / maxValue).toInt()

                for (valIndex in 0..<barCount) {
                    canvas.drawLine(
                        (barIndex * rect).toFloat(),
                        (height - ((valIndex * rect) + (rect))).toFloat(),
                        ((barIndex * rect) + rectSide).toFloat(),
                        (height - ((valIndex * rect) + (rect))).toFloat(),
                        paint
                    )
                }

            }
            update = false
        }

    }

    fun setValue(values: FloatArray) {
        if (values.isNotEmpty()) {
            if (oldFrequencies.isEmpty()) {
                oldFrequencies.addAll(values.asList())
            }
            frequencies.clear()
            frequencies.addAll(values.asList())
            update = true
            invalidate()
        }

    }
}