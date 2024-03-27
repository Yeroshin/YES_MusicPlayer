package com.yes.player.presentation.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class VisualizerView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var width:Int=0
    private var height:Int=0
    private val paint = Paint()
    private val frequencies = mutableListOf<Float>()
    private val oldFrequencies = mutableListOf<Float>()
    private val maxValue = 1
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(h, w, oldh, oldw)
        width=w
        height=h
    }
    private var update:Boolean=false
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (update){
            val rect=width/frequencies.size
            val spacing=rect/8
            val rectSide=rect-(rect/spacing)
            val maxHeightCount=height/rect
            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE
            paint.strokeWidth=rectSide.toFloat()

            for ((barIndex, value) in frequencies.withIndex()) {
                val level= if (value>oldFrequencies[barIndex]){
                    oldFrequencies[barIndex]+0.1f
                }else{
                    oldFrequencies[barIndex]-0.1f
                }
                val barCount=((level*maxHeightCount)/maxValue).toInt()

                for (valIndex in 0..< barCount ){
                    canvas.drawLine(
                        (barIndex*rect).toFloat(),
                        (height -  ((valIndex*rect)+(rect/2))).toFloat(),
                        ((barIndex*rect)+rectSide).toFloat(),
                        (height - ((valIndex*rect)+(rect/2))).toFloat(),
                        paint
                    )
                }

            }
            update=false
        }

    }

    fun setValue(values: FloatArray) {
        if (values.isNotEmpty()){
            oldFrequencies.clear()
            oldFrequencies.addAll(frequencies)
            frequencies.clear()
            frequencies.addAll(values.asList())
            update=true
            invalidate()
        }

    }
}