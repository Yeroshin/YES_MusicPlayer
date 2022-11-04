package com.yes.player.presentation;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class FontView extends androidx.appcompat.widget.AppCompatTextView{
    public FontView(Context context)
    {
        super(context);
        init(context);
    }

    public FontView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public FontView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context){
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "a_lcdnova_allfont.ttf");
        this.setTypeface(tf);
    }
}
