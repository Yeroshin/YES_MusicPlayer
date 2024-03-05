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
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.util.TypedValue.applyDimension
import android.view.MotionEvent
import androidx.core.graphics.drawable.toBitmap
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@SuppressLint("UseCompatLoadingForDrawables")
class CircularSeekBar(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatSeekBar(context, attrs) {

    private var onProgressChangeListener: OnProgressChangeListener? = null

    interface OnProgressChangeListener {
        fun onProgressChanged(progress: Int)
    }

    fun setOnProgressChangeListener(listener: OnProgressChangeListener) {
        onProgressChangeListener = listener
    }


    private var attrProgressDrawable: LayerDrawable? = null
    private val rect = RectF()
    private val paint = Paint()
    private val matrix = Matrix()

    // private var bounds: Rect? = null
    /*private val bounds by lazy {
           attrProgressDrawable?.getDrawable(2)?.copyBounds()
    }*/
    private var layer: Bitmap? = null
    private val layers: MutableList<Bitmap> by lazy {
        val bitmap =
            (progressDrawable as LayerDrawable).findDrawableByLayerId(android.R.id.progress)
                .toBitmap(
                    rect.right.toInt(),
                    rect.bottom.toInt()
                )
        val backgroundBitmap =
            (progressDrawable as LayerDrawable).findDrawableByLayerId(android.R.id.background)
                .toBitmap(
                    rect.right.toInt(),
                    rect.bottom.toInt()
                )
        mutableListOf(backgroundBitmap, bitmap)

    }
    private val drawable: LayerDrawable = progressDrawable as LayerDrawable
    private var progressBitmap: Bitmap? = null
    private var startValue = 0
    private var endValue = 360
    private var prevAngle = 0
    private var progressValue = 0
    private var thumbOffset=0

    init {
        var offset=0
        context.obtainStyledAttributes(
            attrs,
            intArrayOf(
                android.R.attr.progressDrawable,
                android.R.attr.thumbOffset
            )
        ).apply {

            try {
                attrProgressDrawable = resources.getDrawable(
                    getResourceId(0, 0),
                    context.theme
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
        //  max=endValue


    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()


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
        progressValue = value
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        updateProgressBitmap()
        /* val state=if (enabled){

         }
         (progressDrawable as StateListDrawable).setState()*/
    }

    override fun onDraw(canvas: Canvas) {


        canvas.drawArc(
            rect,
            startValue.toFloat(),
            (progressValue - startValue).toFloat(),
            false,
            paint
        )
        for (layer in layers) {
            canvas.drawBitmap(
                layer,
                matrix,
                null
            )
        }

        /*  val th=thumb.toBitmap(
              rect.right.toInt(),
              rect.bottom.toInt()
          )
          matrix.postRotate(progressValue.toFloat())
          canvas.drawBitmap(
              th,
              matrix,
              null
          )*/

        /*   layer?.let {
               canvas.drawBitmap(
                   it,
                   matrix,
                   null
               )
           }*/
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
        /*  val bitmap=(progressDrawable as LayerDrawable).findDrawableByLayerId(android.R.id.progress)
              .toBitmap(
              rect.right.toInt(),
              rect.bottom.toInt()
          )

         val backgroundBitmap=(progressDrawable as LayerDrawable).findDrawableByLayerId(android.R.id.background).toBitmap(
              rect.right.toInt(),
              rect.bottom.toInt()
          )
          layers[0] = backgroundBitmap*/
        layers.clear()

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
      /*  val rotatedThumb = rotateDrawableToBitmap(
            thumb,
            progressValue.toFloat(),
            rect
        )*/
        val rotatedThumb = rotateDrawable(
            thumb,
            progressValue.toFloat(),
            rect
        )

        layers.add(background)
        layers.add(progress)
        layers.add(secondaryProgress)
        layers.add(rotatedThumb)
        /* for (index in 0..<(progressDrawable as LayerDrawable).numberOfLayers) {

             layers.add(index,
                  (progressDrawable as LayerDrawable).getDrawable(index).toBitmap(
                     rect.right.toInt(),
                     rect.bottom.toInt()
                 )
             )
         }
         /* layer = clipDrawable(
              bitmap,
              (progressValue - startValue).toFloat()
          )*/
         layers.getOrNull(1)?.let {
             if (isEnabled) {
                 layers[1] = clipDrawable(
                     layers[1],
                     (progressValue - startValue).toFloat()
                 )
             }

         }*/



        invalidate()
        /* val l=clipDrawable(
             bitmap,
             (progressValue - startValue).toFloat()
         )
         layers[1] = l*/
    }

    private fun rotateDrawable(drawable: Drawable, angle: Float, rect: RectF): Bitmap {
        val bitmap =
            Bitmap.createBitmap(rect.right.toInt(), rect.bottom.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val radius=thumbOffset
        val drawableWidth = drawable.intrinsicWidth
        val drawableHeight = drawable.intrinsicHeight
        val angleRadians = Math.toRadians(angle.toDouble())
        val drawableCenterX = centerX + radius * cos(angleRadians).toFloat()
        val drawableCenterY = centerY + radius * sin(angleRadians).toFloat()
        val drawableLeft = (drawableCenterX - drawableWidth / 2).toInt()
        val drawableTop = (drawableCenterY - drawableHeight / 2).toInt()

        // Устанавливаем границы для Drawable
        drawable.setBounds(drawableLeft, drawableTop, drawableLeft + drawableWidth, drawableTop + drawableHeight)

        // Отрисовываем Drawable
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

              /*  if (movedAngle < startValue) {
                    movedAngle += 360
                }*/

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

                true
            }

            else -> {
                return false
            }
        }

    }


}