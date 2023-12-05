package com.yes.alarmclockfeature.presentation.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

interface Observer {
    fun onEndIndex()
    fun onStartIndex()
}

class DataPickerView : View {
    var canvasW = 0
    var canvasH = 0
    var position = 0
    var correction = 0
    var position1 = 0
    var position2 = 0
    var index = 0
    var initPosition = 0f
    var roundPos = 0f
    var needPos = 0
    var needAnimate = false
    var actionUP = false
    var numbers: Array<String?> = emptyArray()
    var itemHeight = 0
    var speed = 10
    var gravitySpeed = 10
    var delta = 0f
    var scrollspeed = 0
    var initScrollSpeed = 0
    var actdownTime: Long = 0
    var currentValue: String? = null
    var previousIndex = 0
    var startIndex = 0
    var numbersArray: ArrayList<*>? = null
    var up = false
    var down = false
    var observer: Observer? = null
    fun registerObserver(observer: Observer?) {
        this.observer = observer
    }

    fun init(numbers: IntArray, startIndex: Int) {
        this.startIndex = startIndex
        this.numbers = arrayOfNulls(numbers.size)
        for (i in numbers.indices) {
            this.numbers[i] = String.format("%1$02d", numbers[i])
        }
    }

    constructor(context: Context?) : super(context) {
        numbersArray = ArrayList<Any?>()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        canvasW = w
        canvasH = h
        super.onSizeChanged(w, h, oldw, oldh)
        itemHeight = canvasH / 3
        correction = 4
        position = -(startIndex + correction / 2) * itemHeight
    }

    fun moveBackward() {
        actionUP = true
        val distance = -itemHeight
        val time = 300
        initScrollSpeed = distance * 1000 / time
        scrollspeed = initScrollSpeed
        invalidate()
    }

    fun moveForward() {
        actionUP = true
        val distance = itemHeight
        val time = 300
        initScrollSpeed = distance * 1000 / time
        scrollspeed = initScrollSpeed
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.textSize = (canvasH / 3).toFloat()
        paint.color = Color.WHITE

        val txtWidthArr = FloatArray(numbers[0]!!.length)
        paint.getTextWidths(numbers[0], txtWidthArr)
        val bounds = Rect()
        paint.getTextBounds(numbers[0], 0, numbers[0]!!.length, bounds)
        var txtWidth = 0
        for (i in txtWidthArr.indices) {
            txtWidth = (txtWidth + txtWidthArr[i]).toInt()
        }

        ///////////////////////
        if (position > 0) {
            position = position - numbers.size * itemHeight
        } else if (position < -numbers.size * itemHeight) {
            position = position + numbers.size * itemHeight
        }
        //////////////////////
        index = index(position, 2)
        //////////////////////
        for (i in 0..4) {
            canvas.drawText(
                numbers[index(position, i)]!!,
                (canvasW / 2 - txtWidth / 2).toFloat(),
                (myDraw(position, i) - (itemHeight - Math.abs(bounds.top)) / 2).toFloat(),
                paint
            )
            canvas.drawLine(
                (canvasW / 6).toFloat(),
                myDraw(position, i).toFloat(),
                (canvasW / 6 * 5).toFloat(),
                myDraw(position, i).toFloat(),
                paint
            )
        }

        val framePaint = Paint()
        framePaint.setShader(
            LinearGradient(
                0f,
                0f,
                0f,
                (height / 2).toFloat(),
                Color.BLACK,
                Color.TRANSPARENT,
                Shader.TileMode.CLAMP
            )
        )
        canvas.drawPaint(framePaint)
        framePaint.setShader(
            LinearGradient(
                0f,
                height.toFloat(),
                0f,
                (height - height / 2).toFloat(),
                Color.BLACK,
                Color.TRANSPARENT,
                Shader.TileMode.CLAMP
            )
        )
        canvas.drawPaint(framePaint)


////////////////scroll
        if (actionUP) {
            if (scroll()) {
                this.postInvalidateDelayed((1000 / 25).toLong())
            } else if (gravity()) {
                this.postInvalidateDelayed((1000 / 25).toLong())
            }
        }
    }

    fun checkLoop(delta: Float) {

        val nextPos = (position + delta).toInt()
        if (observer != null) {
            if ((nextPos <= -itemHeight * 1.5) and (position > -itemHeight * 1.5)) {
                observer!!.onEndIndex()
            } else if (nextPos <= -numbers.size * itemHeight - itemHeight * 1.5) {
                observer!!.onEndIndex()
            } else if ((nextPos > -itemHeight * 1.5) and (position <= -itemHeight * 1.5)) {
                observer!!.onStartIndex()
            }
        }
    }

    fun scroll(): Boolean {
        return if (scrollspeed > 0) {
            if (scrollspeed - initScrollSpeed / 10 < 0) {
                scrollspeed = 0
                false
            } else {
                checkLoop((-scrollspeed * 50 / 1000).toFloat())
                position += -scrollspeed * 50 / 1000
                scrollspeed -= initScrollSpeed / 10
                true
            }
        } else if (scrollspeed < 0) {
            if (scrollspeed - initScrollSpeed / 10 > 0) {
                scrollspeed = 0
                false
            } else {
                checkLoop((-scrollspeed * 50 / 1000).toFloat())
                position += -scrollspeed * 50 / 1000
                scrollspeed += -initScrollSpeed / 10
                true
            }
        } else {
            false
        }
    }

    fun gravity(): Boolean {
        val itemPos = Math.abs(position - itemHeight * (position / itemHeight))
        return if ((itemPos != 0) and (itemPos != itemHeight)) {
            if (itemPos > itemHeight / 2) {
                if (itemPos + gravitySpeed > itemHeight) {
                    checkLoop(-(itemHeight - itemPos).toFloat())
                    position -= itemHeight - itemPos
                    invalidate()
                    false
                } else {
                    checkLoop(-gravitySpeed.toFloat())
                    position -= gravitySpeed
                    true
                }
            } else {
                if (itemPos - gravitySpeed < 0) {
                    checkLoop(itemPos.toFloat())
                    position += itemPos
                    invalidate()
                    false
                } else {
                    checkLoop(gravitySpeed.toFloat())
                    position += gravitySpeed
                    true
                }
            }
        } else {
            false
        }
    }
    fun myDraw(posit: Int, ind: Int): Int {
        var posit2 = 0
        val tmp1 = posit / itemHeight
        val tmp2 = itemHeight * tmp1
        posit2 = posit + itemHeight * ind - tmp2
 return posit2
    }

    fun index(posit: Int, ind: Int): Int {
        index = (posit - itemHeight * ind) / itemHeight
        if (index > -4) {
            index = numbers.size - index - correction
        } else if (index < -3) {
            index += correction
        }

        if (index < -numbers.size + 1 || index > numbers.size - 1) {
            index = 0
        }
        ///////////////
        return Math.abs(index)
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        if (motionEvent.action == MotionEvent.ACTION_DOWN) {
            actionUP = false
            scrollspeed = 0
            initPosition = motionEvent.y
            position1 = motionEvent.y.toInt()
            actdownTime = motionEvent.eventTime
        }
        if (motionEvent.action == MotionEvent.ACTION_MOVE) {
            delta = (motionEvent.y - initPosition).toInt().toFloat()
            initPosition = motionEvent.y
            checkLoop(delta)
            position = (position + delta).toInt()

            invalidate()
        }
        if (motionEvent.action == MotionEvent.ACTION_UP) {
            actionUP = true

            val distance = (position1 - motionEvent.y).toInt()
            if (motionEvent.eventTime - actdownTime < 300 && Math.abs(position1 - motionEvent.y) > itemHeight) {
                val time = motionEvent.eventTime - actdownTime
                initScrollSpeed = (distance * 1000 / time).toInt()
                scrollspeed = initScrollSpeed
            } else {
                scrollspeed = 0
            }
            invalidate()
        }
        return true
    }
}
