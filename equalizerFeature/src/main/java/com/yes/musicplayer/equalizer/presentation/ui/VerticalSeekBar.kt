package com.yes.musicplayer.equalizer.presentation.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar


class VerticalSeekBar(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatSeekBar(context, attrs) {
    interface OnVerticalSeekBarChangeListener {
        fun onStartTrackingTouch(seekBar: SeekBar, progress: Int, fromUser: Boolean)
        fun onStopTrackingTouch(seekBar: SeekBar,progress: Int)
    }
    private var onVerticalSeekBarChangeListener: OnVerticalSeekBarChangeListener? = null
    fun setOnVerticalSeekBarChangeListener(listener: OnVerticalSeekBarChangeListener) {
        onVerticalSeekBarChangeListener = listener
    }

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
        onSizeChanged(width, height, 0, 0)
        super.onDraw(canvas)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN->{}
             MotionEvent.ACTION_MOVE -> {
               // val progress = max - (max * event.y / height).toInt()
                progress = max - (max * event.y / height).toInt()
                onSizeChanged(width, height, 0, 0)
                invalidate()
                onVerticalSeekBarChangeListener?.onStartTrackingTouch(this,progress,true)
            }

            MotionEvent.ACTION_UP -> {
                onVerticalSeekBarChangeListener?.onStopTrackingTouch(this,progress)
            }
            MotionEvent.ACTION_CANCEL -> {}
        }
        return true
    }
}
