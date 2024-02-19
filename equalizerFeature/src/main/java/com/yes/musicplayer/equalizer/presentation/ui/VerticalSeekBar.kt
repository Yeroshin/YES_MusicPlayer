package com.yes.musicplayer.equalizer.presentation.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar


class VerticalSeekBar(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatSeekBar(context, attrs) {

    private var w = 0
    private var h = 0
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(h, w, oldh, oldw)
        progressDrawable.setBounds(0, 0, h - paddingLeft - paddingRight, w)
    }
    fun setValue(value: Int) {
        progress = value
        onSizeChanged(width, height, 0, 0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredWidth)
    }
    override fun onDraw(canvas: Canvas) {

        canvas.rotate(-90f, 0f, 0f)
        canvas.translate(-height.toFloat(), -h.toFloat() + w / 2)
        super.onDraw(canvas)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val progress: Int = max - (max * event.y / height).toInt()
               setProgress(progress)
               onSizeChanged(width, height, 0, 0)
                invalidate()
            }

            MotionEvent.ACTION_UP -> {}
            MotionEvent.ACTION_CANCEL -> {}
        }
        return true
    }
}
