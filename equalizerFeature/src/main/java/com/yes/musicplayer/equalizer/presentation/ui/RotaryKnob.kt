package com.yes.musicplayer.equalizer.presentation.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

@Deprecated("doesent used")
class RotaryKnob(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {
    private var background: Drawable? = null
    private var mPaint = Paint()
    private var Rad = 0.0
    private var innerR = 0.0
    private var outerR = 0.0
    var startX = 0
    var startY = 0
    private var stopX = 0
    private var stopY = 0
    private var knobAngle = 269.0
    private var initAngle = 1.0
    private var MAX = 270
    private var positionX = 0
    private var positionY = 0
    private var viewWidth = 0
    private var viewHeight = 0
    private var Mywidth = 0
    private var w = 0
    private var h = 0
    private var Myenabled = false

    interface OnRotaryKnobChangeListener {
        fun onProgress(rotaryKnob: RotaryKnob?)
        fun onStopTrackingTouch(rotaryKnob: RotaryKnob?)
    }

    private var mOnRotaryKnobChangeListener: OnRotaryKnobChangeListener? = null
    fun setOnRotaryKnobChangeListener(l: OnRotaryKnobChangeListener?) {
        mOnRotaryKnobChangeListener = l
    }

    fun removeOnRotaryKnobChangeListener() {
        mOnRotaryKnobChangeListener = null
    }

    private fun onProgress() {
        if (mOnRotaryKnobChangeListener != null) {
            mOnRotaryKnobChangeListener!!.onProgress(this)
        }
    }

    fun onStopTrackingTouch() {
        if (mOnRotaryKnobChangeListener != null) {
            mOnRotaryKnobChangeListener!!.onStopTrackingTouch(this)
        }
    }

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // Drawable d = getCurrentDrawable();
        viewWidth = measuredWidth
        viewHeight = measuredHeight
        Mywidth = Math.min(viewWidth, viewHeight)
        setMeasuredDimension(Mywidth, Mywidth)
        // setMeasuredDimension((int)Rad*2,(int)Rad*2);
        // setMeasuredDimension(width,height);
        // resolveSizeAndState(400, 400, 0);
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(h, w, oldh, oldw)
        this.w = w
        this.h = h
        //////////////////////////////
        Mywidth = Math.min(w, h)
        //////////////////////////////
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        var delta: Double
        val f = cos(Math.toRadians(45.0))
        val t = Math.toDegrees(acos(f))
        return if (event.y < Mywidth && event.x < Mywidth) {
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    initAngle = -Math.toDegrees(
                        Math.atan2(
                            (event.y - Mywidth / 2).toDouble(),
                            (event.x - Mywidth / 2).toDouble()
                        )
                    )
                    if (initAngle < 0) {
                        initAngle += 360
                    }
                    // float a=Math.atan2();
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    var movedAngle = -Math.toDegrees(
                        Math.atan2(
                            (event.y - Mywidth / 2).toDouble(),
                            (event.x - Mywidth / 2).toDouble()
                        )
                    )
                    if (movedAngle < 0) {
                        movedAngle += 360
                    }
                    //////////////////////////////
                    delta = movedAngle - initAngle
                    if (delta < -MAX) {
                        delta += 360
                    }
                    if (delta > MAX) {
                        delta -= 360
                    }
                    knobAngle = if (knobAngle + delta >= MAX) {
                        MAX.toDouble()
                    } else if (knobAngle + delta <= 0) {
                        1.0
                    } else {
                        knobAngle + delta
                    }


                    //////////////////////////////////////////////////////
                    initAngle = movedAngle
                    invalidate()
                    onProgress()
                    true
                }

                MotionEvent.ACTION_UP -> {
                    onStopTrackingTouch()
                    true
                }

                MotionEvent.ACTION_CANCEL -> true
                MotionEvent.ACTION_OUTSIDE -> true
                else -> super.onTouchEvent(event)
            }
        } else super.onTouchEvent(event)
    }

    fun setKnob(knob: Drawable?) {
        background = knob
        setBackground(knob)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f
        ////////////////////////////////
        /*  Bitmap back = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(back);
        int l=(int)(((float)width)/100*90);
        int s=(int)(((float)width)/100*10);
        c.drawBitmap(((BitmapDrawable)background).getBitmap(), null, new Rect(s, s, l, l), null);
        Drawable drawable = new BitmapDrawable(getResources(), back);
        setBackground(drawable);*/
        ///////////////////////////////
    }
    private val shader = LinearGradient(
        (positionX - Rad / 13).toFloat(),
        (positionY - Rad / 13).toFloat(),
        (positionX + Rad / 13).toFloat(),
        (positionY + Rad / 13).toFloat(),
        intArrayOf(
            Color.BLACK, Color.WHITE
        ),
        null,
        Shader.TileMode.CLAMP
    )
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /////////////////////////////////
      //  val back = Bitmap.createBitmap(Mywidth, Mywidth, Bitmap.Config.ARGB_8888)
        // Canvas c = new Canvas(back);
        val l = (Mywidth.toFloat() / 100 * 90).toInt()
        val s = (Mywidth.toFloat() / 100 * 10).toInt()
        //  canvas.drawBitmap(((BitmapDrawable)background).getBitmap(), null, new Rect(s, s, l, l), null);

        /////////////////////////////////
        Rad = (Mywidth / 2).toDouble()

        /* background.setBounds(width/100*10, width/100*10, width/100*90, width/100*90);
       this.setBackground(background);*/
        //  Drawable d = getResources().getDrawable(R.drawable.knob_plastic, null);
        var i = 0
        while (i < MAX) {
            if (i % 30 != 0) {
                innerR = Rad / 100 * 85
                outerR = Rad / 100 * 95
            } else {
                innerR = Rad / 100 * 85
                outerR = Rad / 100 * 98
            }
            if (i < knobAngle) {
                mPaint.color = Color.rgb(20, 20, 20)
            } else if (Myenabled && i > MAX * 0.3f) {
                mPaint.color = Color.rgb(0, 255, 0)
            } else if (Myenabled) {
                mPaint.color = Color.rgb(255, 0, 0)
            }
            startX = Mywidth / 2 + (innerR * cos(Math.toRadians(i.toDouble()))).toInt()
            startY = Mywidth / 2 - (innerR * sin(Math.toRadians(i.toDouble()))).toInt()
            stopX = Mywidth / 2 + (outerR * cos(Math.toRadians(i.toDouble()))).toInt()
            stopY = Mywidth / 2 - (outerR * sin(Math.toRadians(i.toDouble()))).toInt()
            canvas.drawLine(
                startX.toFloat(), startY.toFloat(), stopX.toFloat(), stopY.toFloat(),
                mPaint
            )
            i += 6
        }
        /////pointer
        positionX = Mywidth / 2 + (Rad / 100 * 25 * cos(Math.toRadians(knobAngle))).toInt()
        positionY = Mywidth / 2 - (Rad / 100 * 25 * sin(Math.toRadians(knobAngle))).toInt()
        // RectF rect=new RectF((int)(positionX-Rad/20), (int)(positionX+Rad/20), positionX, (int)(positionY+Rad/20));

        mPaint.setShader(shader)
        mPaint.style = Paint.Style.FILL
        // mPaint.setColor(Color.rgb(0, 0, 0));


        //  canvas.drawOval(rect,mPaint);
        canvas.drawCircle(positionX.toFloat(), positionY.toFloat(), Rad.toFloat() / 13, mPaint)
        mPaint.setShader(null)
        if (Myenabled) {
            mPaint.color = Color.rgb(0, 255, 0)
        } else {
            mPaint.color = Color.rgb(100, 100, 100)
        }
        positionX = Mywidth / 2 + (Rad / 100 * 25 * cos(Math.toRadians(knobAngle))).toInt()
        positionY = Mywidth / 2 - (Rad / 100 * 25 * sin(Math.toRadians(knobAngle))).toInt()
        canvas.drawCircle(positionX.toFloat(), positionY.toFloat(), Rad.toFloat() / 20, mPaint)
    }
}
