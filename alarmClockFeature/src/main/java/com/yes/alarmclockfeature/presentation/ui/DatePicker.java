package com.yes.alarmclockfeature.presentation.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import java.util.Calendar;
/*
public class DatePicker extends LinearLayout implements Observer{
    Context context;
    DataPickerView minuteDataPicker;
    DataPickerView hourDataPicker;
    public DatePicker(Context context) {
        super(context);

    }
    public DatePicker(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.context=context;
        init();

    }
    void init(){

        int paddingPX=13;
        int marginsPX=5;
        int heightPX=150;
        final float scale = getResources().getDisplayMetrics().density;
        int marginsDP = (int) (marginsPX * scale + 0.5f);
        int paddingDP = (int) (paddingPX * scale + 0.5f);
        int heightDP = (int) (heightPX* scale + 0.5f);
        ////////////
        setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, heightDP,1.0f);

        params.setMargins(marginsDP, marginsDP, marginsDP, marginsDP);
//////////////////////////////////////////////
        FrameLayout frameLayoutHour=new FrameLayout(context);
        frameLayoutHour.setLayoutParams(params);
        frameLayoutHour.setBackground(ContextCompat.getDrawable(context, R.drawable.display));
        frameLayoutHour.setPadding(paddingDP,paddingDP,paddingDP,paddingDP);
        addView(frameLayoutHour);
//////////////////////
        hourDataPicker=new DataPickerView(context);
        hourDataPicker.setBackground(ContextCompat.getDrawable(context, R.drawable.picker_barrel));
        frameLayoutHour.addView(hourDataPicker);


////////////////////////////////////////////////
        FrameLayout frameLayoutMinute=new FrameLayout(context);
        frameLayoutMinute.setLayoutParams(params);
        frameLayoutMinute.setBackground(ContextCompat.getDrawable(context, R.drawable.display));
        frameLayoutMinute.setPadding(paddingDP,paddingDP,paddingDP,paddingDP);
        addView(frameLayoutMinute);
////////////////////////
        minuteDataPicker=new DataPickerView(context);
        minuteDataPicker.setBackground(ContextCompat.getDrawable(context, R.drawable.picker_barrel));
        frameLayoutMinute.addView(minuteDataPicker);

        minuteDataPicker.registerObserver(this);
        ///////////////////////
        Calendar calendar = Calendar.getInstance();
/////////////////////////////////////////////////
        int[] minutes=new int[60];
        for(int i=0;i<60;i++){
            minutes[i]=i;
        }

        minuteDataPicker.init(minutes, calendar.get(Calendar.MINUTE));

        int[] hours=new int[24];
        for(int i=0;i<24;i++){
            hours[i]=i;
        }
        hourDataPicker.init(hours, calendar.get(Calendar.HOUR_OF_DAY));

        ////////////////////////////////////////
    }
    int getMinute(){
        return minuteDataPicker.index;
    }
    int getHour(){
        return hourDataPicker.index;
    }
    @Override
    public void onEndIndex() {
        hourDataPicker.moveForward();
    }

    @Override
    public void onStartIndex() {
        hourDataPicker.moveBackward();
    }
}
*/