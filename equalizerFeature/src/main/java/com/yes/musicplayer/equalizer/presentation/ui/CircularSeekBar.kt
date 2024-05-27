package com.yes.musicplayer.equalizer.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.util.TypedValue.applyDimension
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
class CircularSeekBar(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatSeekBar(context, attrs) {

    private var onProgressChangeListener: OnProgressChangeListener? = null

    interface OnProgressChangeListener {
        fun onStartTrackingTouch(progress: Int)
        fun onStopTrackingTouch(progress: Int)
    }

    fun setOnProgressChangeListener(listener: OnProgressChangeListener) {
        onProgressChangeListener = listener
    }


    private var attrProgressDrawable: LayerDrawable? = null
    private val rect = RectF()
    private val paint = Paint()
    private val matrix = Matrix()


    private val layers: MutableList<Bitmap> = mutableListOf()
    private val drawable: LayerDrawable = progressDrawable as LayerDrawable
    private var startValue = 0
    private var endValue = 360
    private var prevAngle = 0
    private var progressValue = 0
    private var thumbOffset=0

    init {
        context.obtainStyledAttributes(
            attrs,
            intArrayOf(
                android.R.attr.progressDrawable,
                android.R.attr.thumbOffset
            )
        ).apply {

            try {
                attrProgressDrawable = ContextCompat.getDrawable(
                    context,
                    getResourceId(0, 0)
                ) as LayerDrawable


                thumbOffset = applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    getDimensionPixelSize(1, 0).toFloat(),
                    resources.displayMetrics
                ).toInt()
            } catch (e: Exception) {
                val a = e
            } finally {
                recycle()
            }
        }
        thumb = null // Скрываем стандартный "бегунок"
    }

    fun setMinValue(value: Int) {
        progressValue = value
        startValue = value
        invalidate()
    }

    fun setMaxValue(value: Int) {
        endValue = value
    }

    fun setProgressValue(value: Int) {
        progressValue = if (value+startValue>endValue){
            endValue
        }else if(value+startValue<startValue){
            startValue
        }else{
            (value*(endValue-startValue)/100)+startValue
        }
        updateProgressBitmap()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        updateProgressBitmap()
    }

    override fun onDraw(canvas: Canvas) {


      /*  canvas.drawArc(
            rect,
            startValue.toFloat(),
            (progressValue - startValue).toFloat(),
            false,
            paint
        )*/
        for (layer in layers) {
            canvas.drawBitmap(
                layer,
                matrix,
                null
            )
        }
    }


    private fun clipDrawable(background: Bitmap, angle: Float): Bitmap {
        val canvas = Canvas(background)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        //  paint.color = Color.BLUE
        canvas.drawPath(
            getSeeThroughPath(background.width, background.height, angle),
            paint
        )

        return background
    }

    private fun getSeeThroughPath(width: Int, height: Int, angle: Float): Path {

        val rect = RectF(
            0f,
            0f,
            width.toFloat(),
            height.toFloat()
        )
        val path = Path()
        path.moveTo(
            (width / 2).toFloat(),
            (height / 2).toFloat()
        )
        path.lineTo(
            (width).toFloat(),
            (height / 2).toFloat()
        )
        path.arcTo(rect, startValue.toFloat(), angle)

        path.close()

        return path
    }


    private var centerX: Double = 0.0
    private var centerY: Double = 0.0// Координаты центра круга


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //////////////////////////
        centerX = (w / 2).toDouble()
        centerY = (h / 2).toDouble()
        val radius = min(centerX, centerY) //- 20f

        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f
        rect.set(
            (centerX - radius).toFloat(),
            (centerY - radius).toFloat(),
            (centerX + radius).toFloat(),
            (centerY + radius).toFloat()
        )

        updateProgressBitmap()

    }

    private fun updateProgressBitmap() {
        layers.clear()
        if (rect.right>0&&rect.bottom>0){
            val background = drawable
                .findDrawableByLayerId(android.R.id.progress).toBitmap(
                    rect.right.toInt(),
                    rect.bottom.toInt()
                )
            val progress = (progressDrawable as LayerDrawable)
                .findDrawableByLayerId(android.R.id.background).toBitmap(
                    rect.right.toInt(),
                    rect.bottom.toInt()
                )
            if (isEnabled) {
                clipDrawable(
                    progress,
                    (progressValue - startValue).toFloat()
                )
            }

            val secondaryProgress = (progressDrawable as LayerDrawable)
                .findDrawableByLayerId(android.R.id.secondaryProgress).toBitmap(
                    rect.right.toInt(),
                    rect.bottom.toInt()
                )
            val thumb = if (isEnabled) {
                (progressDrawable as LayerDrawable)
                    .findDrawableByLayerId(android.R.id.button1)
            } else {
                (progressDrawable as LayerDrawable)
                    .findDrawableByLayerId(android.R.id.button2)
            }

            val rotatedThumb = rotateDrawable(
                thumb,
                progressValue.toFloat(),
                thumbOffset
            )

            layers.add(background)
            layers.add(progress)
            layers.add(secondaryProgress)
            layers.add(rotatedThumb)

            invalidate()
        }else{
            println("width and height is less than 0")
        }


    }

    private fun rotateDrawable(drawable: Drawable, angle: Float, radius:Int): Bitmap {
        val bitmap =
            Bitmap.createBitmap(rect.right.toInt(), rect.bottom.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight
        val angleRadians = Math.toRadians(angle.toDouble())
        val drawableCenterX = centerX + radius * cos(angleRadians).toFloat()
        val drawableCenterY = centerY + radius * sin(angleRadians).toFloat()
        val drawableLeft = (drawableCenterX - drawableWidth / 2).toInt()
        val drawableTop = (drawableCenterY - drawableHeight / 2).toInt()

        drawable.setBounds(drawableLeft, drawableTop, drawableLeft + drawableWidth, drawableTop + drawableHeight)

        drawable.draw(canvas)
        return Bitmap.createBitmap(bitmap)
    }
    private fun rotateDrawableToBitmap(drawable: Drawable, angle: Float, rect: RectF): Bitmap {

        val bitmap =
            Bitmap.createBitmap(rect.right.toInt(), rect.bottom.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.rotate(angle, rect.right / 2, rect.bottom / 2)
        drawable.setBounds(
            0,
            0,
            rect.right.toInt(),
            rect.bottom.toInt()
        )
        drawable.draw(canvas)
        return Bitmap.createBitmap(bitmap)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                prevAngle = Math.toDegrees(
                    atan2(
                        (event.y - centerY),
                        (event.x - centerX)
                    )
                ).toInt().let {
                    if (it < startValue) it + 360 else it
                }
                true
            }

            MotionEvent.ACTION_MOVE -> {
                var movedAngle = Math.toDegrees(
                    atan2(
                        (event.y - centerY),
                        (event.x - centerX)
                    )
                ).toInt().let {
                        if (it < startValue) it + 360 else it
                    }


                if (prevAngle < startValue + 90 && movedAngle > endValue - 90) {
                    movedAngle = startValue
                } else if (prevAngle > endValue - 90 && movedAngle < startValue + 90) {
                    prevAngle = -(endValue - prevAngle)
                }
                val delta = movedAngle - prevAngle
                prevAngle = movedAngle
                progressValue += delta
                progress += delta

                if (progressValue < startValue) {
                    progressValue = startValue
                } else if (progressValue > endValue) {
                    progressValue = endValue
                }
                /////////////////////
                updateProgressBitmap()
                //////////////////////
                onProgressChangeListener?.onStartTrackingTouch(
                    (progressValue-startValue)*100/(endValue-startValue)
                )
                true
            }
            MotionEvent.ACTION_UP ->{
                onProgressChangeListener?.onStopTrackingTouch(
                    (progressValue-startValue)*100/(endValue-startValue)
                )
                true
            }
            else -> {
                return false
            }
        }

    }


}