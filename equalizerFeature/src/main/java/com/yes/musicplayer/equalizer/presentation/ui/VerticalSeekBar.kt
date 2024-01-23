package com.yes.musicplayer.equalizer.presentation.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar

class VerticalSeekBar(context:Context,  attrs:AttributeSet): AppCompatSeekBar( context,  attrs) {
    private var initValue = 0
    private var mPaint: Paint? = null
    private var top = 0
    private var bottom = 0
    private var left = 0
    private var right = 0
    private var h = 0
    private var w = 0
    private var thumbW = 0
    private var thumbH = 0
    private var width = 0
    private var height = 0
    private var thumb: Drawable? = null
    private var enabled = true
    private var measured = false

    interface OnSeekBarChangeListener {
        fun onProgress(seekBar: VerticalSeekBar?)
        fun onStopTrackingTouch(seekBar: VerticalSeekBar?)
    }

    var mOnSeekBarChangeListener: OnSeekBarChangeListener? = null
    fun setOnSeekBarChangeListener(l: OnSeekBarChangeListener?) {
        mOnSeekBarChangeListener = l
    }

    fun onProgress() {
       /* if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onProgress(this)
        }*/
    }

    fun onStopTrackingTouch() {
       /* if (mOnSeekBarChangeListener != null) {
            mOnSeekBarChangeListener.onStopTrackingTouch(this)
        }*/
    }

  /*  fun VerticalSeekBar(context: Context?, attrs: AttributeSet?) {
        super(context, attrs)
        /*   int[] attrsArray = new int[] {
                android.R.attr.thumb, // 0

        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
         int id = ta.getResourceId(0 , 0);// index of attribute in attrsArray
       // thumb = ta.getDrawable(0);*/
        /*  thumbH=thumb.getIntrinsicWidth();//90angle
        thumbW=thumb.getIntrinsicHeight();//90angle*/


        //match_parent=-1
        //wrap_content=-2
        //   int lw=ta.getInt(2 /* index of attribute in attrsArray */, 0);
        //  int lh=ta.getInt(3 /* index of attribute in attrsArray */, 0);
        //  float w= ta.getFloat(4,0);
        //  int width=background.getIntrinsicWidth();
    }*/

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec,heightMeasureSpec );
        // setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight() );
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(getMeasuredWidth(), heightMeasureSpec)
        // int r=getHeight();
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        this.h = h
        this.w = w
        super.onSizeChanged(h, w, oldh, oldw)
        // int r=getHeight();
    }

    fun setCustomThumb(myThumb: Drawable?) {
        thumb = myThumb
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        this.top = top
        this.bottom = bottom
        this.left = left
        this.right = right
        width = right - left
        height = bottom - top
        mPaint = Paint()
        val r: Int = getHeight()
        /////////////////////////////////////
        if (!measured) {
            val thumbb = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(thumbb)
            canvas.drawBitmap(
                (thumb as BitmapDrawable?)!!.bitmap,
                null,
                Rect(
                    (width * 0.2f).toInt(),
                    (width * 0.15f).toInt(),
                    (width * 0.8f).toInt(),
                    (width * 0.85f).toInt()
                ),
                null
            )
            val drawable: Drawable = BitmapDrawable(getResources(), thumbb)
            setThumb(drawable)
            measured = true
            /* thumbH=drawable.getIntrinsicWidth();//90angle
            thumbW=drawable.getIntrinsicHeight();*/thumbH = (width * 0.6f).toInt()
            thumbW = (width * 0.6f).toInt()
            setValue(initValue)
        }

        ///////////////////////////
    }

    override fun onDraw(canvas: Canvas) {
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = 5f
        val bigStep = (height - thumbH) / 4
        //  float bigStepf=(float)h/5;
        val littleStep = bigStep / 4
        for (i in 0..4) {

            /* int x=height-getProgress()*height/getMax();
            float xf=height-getProgress()*height/getMax();
            int y=height-thumbH/2+bigStep*i;
            float yf=(float)height-((float)thumbH)/2+bigStepf*i;*/
            if (height - getProgress() * height / getMax() > thumbH / 2 + bigStep * i) {
                mPaint!!.color = Color.rgb(21, 25, 25)
            } else if (enabled) {
                mPaint!!.color = Color.rgb(0, 255, 0)
            }
            val m = thumbH / 2
            val t = thumbH / 2 + bigStep * i
            canvas.drawLine(
                (width / 6).toFloat(),
                (thumbH / 2 + bigStep * i).toFloat(),
                (width - width / 6).toFloat(),
                (thumbH / 2 + bigStep * i).toFloat(),
                mPaint!!
            )
            if (i < 4) {
                for (a in 1..3) {
                    /*   int p=getProgress();
                    int h=height;
                    int m=getMax();*/
                    if (height - getProgress() * height / getMax() > thumbH / 2 + bigStep * i + littleStep * a) {
                        mPaint!!.color = Color.rgb(21, 25, 25)
                    } else if (enabled) {
                        mPaint!!.color = Color.rgb(0, 255, 0)
                    }
                    canvas.drawLine(
                        (w / 4).toFloat(),
                        (thumbH / 2 + bigStep * i + littleStep * a).toFloat(),
                        (w - w / 4).toFloat(),
                        (thumbH / 2 + bigStep * i + littleStep * a).toFloat(),
                        mPaint!!
                    )
                }
            }
        }
        val r = Rect(
            width / 3, thumbH / 2 - thumbH / 4, width - width / 3,
            thumbH / 2 + height / 5 * 4 + thumbH / 4
        )
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.color = Color.rgb(0, 0, 0)
        canvas.drawRect(r, mPaint!!)
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.color = Color.rgb(25, 25, 25)
        canvas.drawRect(r, mPaint!!)

        /*  mPaint.setColor(Color.rgb(255, 0, 0));
        canvas.drawLine(0, 0, right, 0, mPaint);*/canvas.rotate(-90f)
        canvas.translate(-getHeight().toFloat(), 0f)
        super.onDraw(canvas)
    }

    fun setValue(value: Int) {
        setProgress(value)
        onSizeChanged(getWidth(), getHeight(), 0, 0)
        onProgress()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled()) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val progress: Int = getMax() - (getMax() * event.y / getHeight()).toInt()
                setProgress(progress)
                onSizeChanged(getWidth(), getHeight(), 0, 0)
                onProgress()
            }

            MotionEvent.ACTION_UP -> onStopTrackingTouch()
            MotionEvent.ACTION_CANCEL -> {}
        }
        return true
    }
}