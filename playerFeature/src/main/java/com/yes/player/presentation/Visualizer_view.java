package com.ys.musicplayer.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.audiofx.Visualizer;
import android.util.AttributeSet;
import android.view.View;

import static android.media.audiofx.Visualizer.MEASUREMENT_MODE_PEAK_RMS;
import static android.media.audiofx.Visualizer.SCALING_MODE_NORMALIZED;

public class Visualizer_view extends View {
    Visualizer mVisualizer;
    double[] magnitudes;
    Paint mPaint;
    int height;
    int width;
    int sampling_rate;
   
    int frequency_count=16;
    int bars_count=16;
    Context context;

    int prev_frequency[];
    public Visualizer_view(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.context=context;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom){
        super.onLayout(changed,left,top,right,bottom);
     /*   if(YESMusicPlayerService.paused){
            Intent serviceIntent = new Intent(context, YESMusicPlayerService.class);
            serviceIntent.putExtra("start_type",YESMusicPlayerService.start_type_player);
            context.startService(serviceIntent);
            while(YESMusicPlayerService.paused){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }*/

       // setAudioSessionId(YESMusicPlayerService.mediaPlayer.getAudioSessionId());
        mPaint = new Paint();
        mPaint.setColor(Color.rgb(0, 0, 0));
        mPaint.setStyle(Paint.Style.STROKE);
        height=getHeight();
        width=getWidth();
        mPaint.setStrokeWidth(width/frequency_count/10*9);
       // int w=width/frequency_count*2;
        prev_frequency=new int[frequency_count+1];

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mVisualizer!=null){
            mVisualizer.release();
            mVisualizer=null;
        }

    }

    @Override
    protected void onDraw(Canvas canvas){
        if(magnitudes!=null){
           //
            int min_step=sampling_rate/magnitudes.length;
            ////////////////////////////////////////////

            ////////////////////////////////////////////
           // int frequency_step=magnitudes.length/frequency_count;
            for(int i=1;i<frequency_count+1;i++){
               // int fr=i*frequency_step;
                double average=0;
                int max_dB=500;
                int y=0;
              //  int frequency_step=magnitudes.length/frequency_count;
               // int frequency=0;
                if(i<frequency_count/2){
                   // frequency=i*22050/512;
                    y=(int)magnitudes[i]*bars_count/ max_dB;

                }else{
                    double py=Math.round(Math.exp((Math.log(16) *  2/(double)frequency_count)*(i-1)));
                    double yy=Math.round(Math.exp((Math.log(16) *  2/(double)frequency_count)*i));
                    double ny=Math.round(Math.exp((Math.log(16) *  2/(double)frequency_count)*(i+1)));

                    double range=Math.round((ny-yy)/2+(yy-py)/2);
                    for(int k=0;k<range;k++){
                       // frequency=((int)(yy-(yy-py)/2)+k)*22050/512;
                        //average+=magnitudes[(int)(yy-(yy-py)/2)+k]*(i/2);
                        average+=magnitudes[(int)(yy-(yy-py)/2)+k];
                    }
                    y=(int)(average/range*bars_count/max_dB);
                    //y=0;
                  /*  int prev_yy =  (int)Math.round( Math.exp((Math.log(16) * (2 / (double)frequency_count)) * (i-1)))*frequency_step;
                    int yy =  (int)Math.round( Math.exp((Math.log(16) *  2/(double)frequency_count) * i))*frequency_step;
                    int next_yy = (int)Math.round( Math.exp((Math.log(16) * (2 / (double)frequency_count)) * (i+1)))*frequency_step;
                    int freq=yy*min_step;
                    if(next_yy>513){
                        next_yy=500;
                    }*/
                 /*   int frequency_range= ((next_yy-yy)/2)+((yy-prev_yy)/2);
                    for(int k=0;k<frequency_range;k++){
                        int tmp=(yy-(yy-prev_yy)/2)+k;
                        average+=magnitudes[(yy-(yy-prev_yy)/2)+k];
                        int t=0;
                    }


                    y=(int)average/frequency_range*bars_count/ max_dB;
                    int a=0;*/
                }


              /*  int bars_count=15;
                int max_dB=105;
               */

               // int y=(int)magnitudes[i*frequency_step]*100/80;
              //  int y=(int)magnitudes[i]*100/10*2;
               // canvas.drawLine(i*width/frequency_count, 0, i*width/frequency_count, y, mPaint);
                if(prev_frequency[i]<y){
                    prev_frequency[i]++;
                }else if(prev_frequency[i]>y){
                    prev_frequency[i]--;
                }
                for(int x=0;x<prev_frequency[i];x++){
                  /*  int starty=x*height/10;
                    int stopy=height/8;
                    int h=starty+stopy;*/
                    canvas.drawLine(i*width/(frequency_count+1), height-x*height/10, i*width/(frequency_count+1), height-x*height/10+height/15, mPaint);
                }

            }

        }

        super.onDraw(canvas);
    }
    public void setAudioSessionId(int audioSessionId) {
        if(mVisualizer!=null){
            return;
        }
        mVisualizer = new Visualizer(audioSessionId);

        if(mVisualizer.getEnabled()){
            mVisualizer.setEnabled(false);
        };
      /*  int a=mVisualizer.getCaptureSize();
        int b=mVisualizer.getSamplingRate();
        int c[]=Visualizer.getCaptureSizeRange();*/
        mVisualizer.setScalingMode(SCALING_MODE_NORMALIZED);
        mVisualizer.setMeasurementMode(MEASUREMENT_MODE_PEAK_RMS);
        int mode =mVisualizer.getMeasurementMode();
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);

        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
             /*   int n = fft.length;
                float[] magnitudes = new float[n / 2 + 1];
                magnitudes[0] = (float)Math.abs(fft[0]);      // DC
                magnitudes[n / 2] = (float)Math.abs(fft[1]);  // Nyquist
                for (int k = 1; k < n / 2; k++) {
                    int i = k * 2;
                    double magnitude=0;
                    magnitudes[k] = Math.hypot(fft[i],fft[i + 1]);
                    int dbValue = (int) (10 * Math.log10(magnitude));
                    magnitude = Math.round(dbValue * 8);
                    magnitudes[k]=magnitude;
                }
                invalidate();*/
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
                sampling_rate=samplingRate/2000;
                int n = fft.length;
                magnitudes = new double[n / 2 + 1];
                float[] phases = new float[n / 2 + 1];
                magnitudes[0] = (float)Math.abs(fft[0]);      // DC
                magnitudes[n / 2] = (float)Math.abs(fft[1]);  // Nyquist
                phases[0] = phases[n / 2] = 0;
                for (int k = 1; k < n / 2; k++) {
                    int i = k * 2;
                    double magnitude=0;
                    magnitude = (float)Math.hypot(fft[i],fft[i + 1]);
                    int dbValue = (int) (20 * Math.log10(magnitude));
                    magnitude = Math.round(dbValue * 8);
                    magnitudes[k]=magnitude;
                    phases[k] = (float)Math.atan2(fft[i + 1], fft[i]);
                    //magnitudes[k] = (float)Math.hypot(fft[i],fft[i + 1]);
                }
              /*  try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                invalidate();
            }
        }, Visualizer.getMaxCaptureRate() / 2, false, true);

        mVisualizer.setEnabled(true);
    }
}
