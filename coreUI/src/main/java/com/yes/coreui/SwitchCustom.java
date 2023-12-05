package com.musicplayer.alarmclock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class SwitchCustom extends androidx.appcompat.widget.SwitchCompat {
    Drawable background;
    public SwitchCustom(Context context) {
        super(context);
    }

    public SwitchCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        int[] attrsArray = new int[] {
                android.R.attr.id, // 0
                android.R.attr.background, // 1
                android.R.attr.layout_width, // 2
                android.R.attr.layout_height,//3
                android.R.attr.rotation// 4
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        int id = ta.getResourceId(0 /* index of attribute in attrsArray */, 0);


      /*  background = ta.getDrawable(1);
        Rect r=background.getBounds();*/
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        // Drawable d = getCurrentDrawable();
      /*  viewWidth= getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        width=Math.min(viewWidth,viewHeight);
        setMeasuredDimension(width ,width);*/
        // setMeasuredDimension((int)Rad*2,(int)Rad*2);
        // setMeasuredDimension(width,height);
        // resolveSizeAndState(400, 400, 0);
    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
      /*  this.w=w;
        this.h=h;
        //////////////////////////////
        width=Math.min(w,h);*/
        //////////////////////////////
    }
}