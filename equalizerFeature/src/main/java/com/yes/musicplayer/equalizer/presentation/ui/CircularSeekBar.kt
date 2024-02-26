package com.yes.musicplayer.equalizer.presentation.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.widget.SeekBar
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import kotlin.io.path.moveTo
import kotlin.math.atan2
import kotlin.math.min

class CircularSeekBar(context: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatSeekBar(context, attrs) {

    private var onProgressChangeListener: OnProgressChangeListener? = null

    init {
        thumb = null // Скрываем стандартный "бегунок"
    }

    override fun onDraw(canvas: Canvas) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = min(centerX, centerY) - 20f

        val angle = progress
        val paint = Paint()
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f
        val oval = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
        canvas.drawArc(
            oval,
            0f,
            angle,
            false,
            paint
        )


        initProgressDrawable?.let {
            val copy=it.constantState!!.newDrawable().mutate()
            progressDrawable = clipCenterPartOfBackgroundImage(
                copy
                    .toBitmap(oval.right.toInt(), oval.bottom.toInt()),
                progress
            ).toDrawable(resources)
            progressDrawable.setBounds(
                oval.left.toInt(),
                oval.top.toInt(),
                oval.right.toInt(),
                oval.bottom.toInt()
            )
            progressDrawable.draw(canvas)
        }?:run{
            initProgressDrawable=progressDrawable

        }





    }
private var initProgressDrawable:Drawable?=null
    private fun clipCenterPartOfBackgroundImage(background: Bitmap,angle: Float): Bitmap {
        val canvas = Canvas(background)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
      //  paint.color = Color.BLUE
        canvas.drawPath(
            getSeeThroughPath(background.width, background.height,angle),
            paint
        )

        return background
    }

    private fun getSeeThroughPath(width: Int, height: Int,angle:Float): Path {

        val rect = RectF(
            0f,
            0f,
            width.toFloat(),
            height.toFloat()
        )
        val path = Path()
        path.moveTo(
            (width/2).toFloat(),
            (height/2).toFloat()
        )
        path.lineTo(
            (width).toFloat(),
            (height/2).toFloat()
        )
        path.arcTo(rect, 0f, angle)
        path.close()

        return path
    }


    var initAngle = 0.0


    var Mywidth = 0
    private var cx = 0.0
    private var cy = 0.0// Координаты центра круга
    private var radius = 0f // Радиус круга
    private var progress = 0f // Текущий прогресс
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cx = (w / 2).toDouble()
        cy = (h / 2).toDouble()
        radius = (Math.min(w, h) / 2 - 10).toFloat()
    }

    var prevAngle = 0
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initAngle = Math.toDegrees(
                    atan2(
                        (event.y.toDouble() - cy),
                        (event.x.toDouble() - cx)
                    )
                )
                if (initAngle < 0) {
                    initAngle += 360f
                }
                prevAngle = initAngle.toInt()
                true

            }

            MotionEvent.ACTION_MOVE -> {
                var movedAngle = Math.toDegrees(
                    atan2(
                        (event.y.toDouble() - cy),
                        (event.x.toDouble() - cx)
                    )
                )
                if (movedAngle < 0) {
                    movedAngle += 360f
                }
                Log.d("movedAngle", movedAngle.toString())
                val delta = movedAngle - prevAngle
                prevAngle = movedAngle.toInt()
                progress += delta.toFloat()
                if (progress < 0) {
                    progress = 0f
                } else if (progress > 270) {
                    progress = 270f
                }
                invalidate()
                true
            }
        }

        return true
    }

    fun setOnProgressChangeListener(listener: OnProgressChangeListener) {
        onProgressChangeListener = listener
    }

    interface OnProgressChangeListener {
        fun onProgressChanged(progress: Int)
    }
}