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
import android.view.MotionEvent
import androidx.core.graphics.drawable.toBitmap
import kotlin.math.atan2
import kotlin.math.min

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
    private var layer:Bitmap?=null
    init {
        context.obtainStyledAttributes(
            attrs,
            intArrayOf(
                android.R.attr.progressDrawable
            )
        ).apply {

            try {
                attrProgressDrawable = resources.getDrawable(
                    getResourceId(0, 0),
                    context.theme
                ) as LayerDrawable
            } catch (e: Exception) {
                val a = e
            } finally {
                recycle()
            }
        }

        /* attrProgressDrawable = (resources.getDrawable(
             context.obtainStyledAttributes(
                 attrs,
                 intArrayOf(
                     android.R.attr.progressDrawable
                 )
             ).getResourceId(0, 0),
             context.theme
         ) as LayerDrawable)*/
        //   bounds = attrProgressDrawable?.getDrawable(2)?.bounds

        thumb = null // Скрываем стандартный "бегунок"
    }

    private var initProgressDrawable: Drawable? = null

    override fun onDraw(canvas: Canvas) {

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = min(centerX, centerY) //- 20f

        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f
        rect.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
        canvas.drawArc(
            rect,
            0f,
            progress.toFloat(),
            false,
            paint
        )
        ////////////////////
     /*   background.setBounds(
            rect.left.toInt(),
            rect.top.toInt(),
            rect.right.toInt(),
            rect.bottom.toInt()
        )*/
      //  progressDrawable.draw(canvas)
        ///////////////////////////

        val layers= mutableListOf<Bitmap>()
      /*  for (i in 0..<(progressDrawable as LayerDrawable).numberOfLayers){
           /* canvas.drawBitmap(
                (progressDrawable as LayerDrawable).getDrawable(i).toBitmap(
                    rect.right.toInt(),
                    rect.bottom.toInt(),
                ),
                matrix,
                null
            )*/
          /*  layers.add(

                (progressDrawable as LayerDrawable).getDrawable(i).toBitmap(
                    rect.right.toInt(),
                    rect.bottom.toInt(),
                )
            )*/
        }*/
        ///////////////////////////
      //  val bounds=(progressDrawable as LayerDrawable).getDrawable(i).bounds
     /*   val progressBitmap=progressDrawable.toBitmap(
            rect.right.toInt(),
            rect.bottom.toInt()
        )*/
     /*   for( layer in layers){
          /*  val bounds=layer.bounds
            val bitmap =layer.toBitmap(
                bounds.right,
                bounds.bottom
            )*/

            canvas.drawBitmap(
                layer,
                matrix,
                null
            )
           /* canvas.drawBitmap(
                bitmap ,
                null,
                Rect(bounds.left,
                    bounds.top,
                    bounds.right,
                    bounds.bottom
                ),
                null
            )*/
          /*  canvas.drawBitmap(
                layer.toBitmap(
                    bounds.right,
                    bounds.bottom
                ),
                null,
                Rect(bounds.left, bounds.top, bounds.right, bounds.bottom),
                null
            )*/
        }*/
       /* canvas.drawBitmap(
            progressBitmap,
            null,
            rect,
            null
        )*/
       /* canvas.drawBitmap(
            progressBitmap,
            matrix,
            null
        )*/
       /* layer=progressDrawable.toBitmap(
            rect.right.toInt(),
            rect.bottom.toInt()
        )*/

         layer?.let {
             canvas.drawBitmap(
                 it,
                 matrix,
                 null
             )
         }
        /////////////////////////
        ///////worked
      /*  val layer1=(progressDrawable as LayerDrawable).getDrawable(0).toBitmap(
            rect.right.toInt(),
            rect.bottom.toInt()
        )*/
      /*  val layer2=(progressDrawable as LayerDrawable).getDrawable(1).toBitmap(
            rect.right.toInt(),
            rect.bottom.toInt()
        )
        clipCenterPartOfBackgroundImage(
            layer2,
            progress
        )*/
       /* canvas.drawBitmap(
            layer1,
            matrix,
            null
        )*/
       /* canvas.drawBitmap(
            layer2,
            matrix,
            null
        )*/
///////////endofworked
        ////////////////////////////
        ////////////////////////////
       /* canvas.drawBitmap(
            progressDrawable.toBitmap(
                rect.right.toInt(),
                rect.bottom.toInt()
            ),
            matrix,
            null
        )*/
        ///////////////////
        /*   initProgressDrawable?.let {
               val bounds = (initProgressDrawable as LayerDrawable).getDrawable(2).bounds
               val layerDrawable = LayerDrawable(
                   arrayOf<Drawable>(
                       (it as LayerDrawable).getDrawable(0),
                       clipCenterPartOfBackgroundImage(
                           it.getDrawable(1).toBitmap(
                               oval.right.toInt(),
                               oval.bottom.toInt()
                           ),
                           progress
                       ).toDrawable(resources),
                       rotateDrawable(
                           it.getDrawable(2),
                           progress.toFloat()
                       )

                   )
               )
               layerDrawable.setLayerInset(
                   2,
                   bounds.left,
                   bounds.top,
                   bounds.right,
                   bounds.bottom
               )
               progressDrawable = layerDrawable


               progressDrawable.draw(canvas)
               //progressDrawable.invalidateSelf()
           } ?: run {
               initProgressDrawable = progressDrawable
               progressDrawable.setBounds(
                   oval.left.toInt(),
                   oval.top.toInt(),
                   oval.right.toInt(),
                   oval.bottom.toInt()
               )
           }*/


    }

    fun rotateDrawable(drawable: Drawable, degrees: Float): Drawable {
        val bitmap = drawable.toBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
        val matrix = Matrix()
        matrix.postRotate(degrees)
        val rotatedBitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        return BitmapDrawable(resources, rotatedBitmap)
    }

    private fun clipCenterPartOfBackgroundImage(background: Bitmap, angle: Int): Bitmap {
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

    private fun getSeeThroughPath(width: Int, height: Int, angle: Int): Path {

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
        path.arcTo(rect, 0f, angle.toFloat())
        path.close()

        return path
    }


    private var cx = 0.0
    private var cy = 0.0// Координаты центра круга


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cx = (w / 2).toDouble()
        cy = (h / 2).toDouble()

    }

    private var prevAngle = 0
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                prevAngle = Math.toDegrees(
                    atan2(
                        (event.y.toDouble() - cy),
                        (event.x.toDouble() - cx)
                    )
                ).toInt()
                if (prevAngle < 0) {
                    prevAngle += 360
                }

                true
            }

            MotionEvent.ACTION_MOVE -> {
                var movedAngle = Math.toDegrees(
                    atan2(
                        (event.y - cy),
                        (event.x - cx)
                    )
                ).toInt()
                if (movedAngle < 0) {
                    movedAngle += 360
                }
                if (prevAngle < 90 && movedAngle > 270) {
                    movedAngle = 0
                } else if (prevAngle > 270 && movedAngle < 90) {
                    prevAngle = -(360 - prevAngle)
                }
                val delta = movedAngle - prevAngle
                prevAngle = movedAngle
                progress += delta
                if (progress < 0) {
                    progress = 0
                } else if (progress > max) {
                    progress = max
                }
                /////////////////////
                layer=clipCenterPartOfBackgroundImage(
                    progressDrawable.toBitmap(
                        rect.right.toInt(),
                        rect.bottom.toInt()
                    ),
                    progress
                )
                //////////////////////
                 invalidate()
                true
            }

            else -> {
                return false
            }
        }

    }


}