package com.yes.player.presentation.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.audiofx.Visualizer
import android.media.audiofx.Visualizer.OnDataCaptureListener
import android.util.AttributeSet
import android.view.View
import kotlin.math.abs

class VisualizerViewOld(con: Context, attrs: AttributeSet?) :
    View(con, attrs) {
    private var mVisualizer: Visualizer? = null
    private var magnitudes: DoubleArray? = null
    private val mPaint: Paint? by lazy {  Paint()}
    private val _height by lazy { getHeight() }
    private val _width by lazy{getWidth()}
    private var samplingRate = 0
    private val frequencyCount = 16
    private val barsCount = 16
    private var prevFrequency: IntArray = intArrayOf()
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
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

        mPaint!!.color = Color.rgb(0, 0, 0)
        mPaint!!.style = Paint.Style.STROKE

        mPaint!!.strokeWidth = (_width / frequencyCount / 10 * 9).toFloat()
        // int w=width/frequency_count*2;
        prevFrequency = IntArray(frequencyCount + 1)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mVisualizer != null) {
            mVisualizer!!.release()
            mVisualizer = null
        }
    }

    override fun onDraw(canvas: Canvas) {
        if (magnitudes != null) {
            //
            val min_step = samplingRate / magnitudes!!.size
            ////////////////////////////////////////////

            ////////////////////////////////////////////
            // int frequency_step=magnitudes.length/frequency_count;
            for (i in 1 until frequencyCount + 1) {
                // int fr=i*frequency_step;
                var average = 0.0
                val max_dB = 500
                var y = 0
                //  int frequency_step=magnitudes.length/frequency_count;
                // int frequency=0;
                if (i < frequencyCount / 2) {
                    // frequency=i*22050/512;
                    y = magnitudes!![i].toInt() * barsCount / max_dB
                } else {
                    val py =
                        Math.round(Math.exp(Math.log(16.0) * 2 / frequencyCount.toDouble() * (i - 1)))
                            .toDouble()
                    val yy =
                        Math.round(Math.exp(Math.log(16.0) * 2 / frequencyCount.toDouble() * i))
                            .toDouble()
                    val ny =
                        Math.round(Math.exp(Math.log(16.0) * 2 / frequencyCount.toDouble() * (i + 1)))
                            .toDouble()
                    val range = Math.round((ny - yy) / 2 + (yy - py) / 2).toDouble()
                    var k = 0
                    while (k < range) {

                        // frequency=((int)(yy-(yy-py)/2)+k)*22050/512;
                        //average+=magnitudes[(int)(yy-(yy-py)/2)+k]*(i/2);
                        average += magnitudes!![(yy - (yy - py) / 2).toInt() + k]
                        k++
                    }
                    y = (average / range * barsCount / max_dB).toInt()
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
                if (prevFrequency[i] < y) {
                    prevFrequency[i]++
                } else if (prevFrequency[i] > y) {
                    prevFrequency[i]--
                }
                for (x in 0 until prevFrequency[i]) {
                    /*  int starty=x*height/10;
                    int stopy=height/8;
                    int h=starty+stopy;*/
                    canvas.drawLine(
                        (i * _width / (frequencyCount + 1)).toFloat(),
                        (_height - x * _height / 10).toFloat(),
                        (i * _width / (frequencyCount + 1)).toFloat(),
                        (_height - x * _height / 10 + _height / 15).toFloat(),
                        mPaint!!
                    )
                }
            }
        }
        super.onDraw(canvas)
    }

    fun setAudioSessionId(audioSessionId: Int) {
        if (mVisualizer != null) {
            return
        }
        mVisualizer = Visualizer(audioSessionId)
        if (mVisualizer!!.enabled) {
            mVisualizer!!.enabled = false
        }
        /*  int a=mVisualizer.getCaptureSize();
        int b=mVisualizer.getSamplingRate();
        int c[]=Visualizer.getCaptureSizeRange();*/mVisualizer!!.scalingMode =
            Visualizer.SCALING_MODE_NORMALIZED
        mVisualizer!!.measurementMode = Visualizer.MEASUREMENT_MODE_PEAK_RMS
        val mode = mVisualizer!!.measurementMode
        mVisualizer!!.captureSize = Visualizer.getCaptureSizeRange()[1]
        mVisualizer!!.setDataCaptureListener(object : OnDataCaptureListener {
            override fun onWaveFormDataCapture(
                visualizer: Visualizer,
                fft: ByteArray,
                samplingRate: Int
            ) {
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

            override fun onFftDataCapture(
                visualizer: Visualizer,
                fft: ByteArray,
                samplingRate: Int
            ) {
                this@VisualizerViewOld.samplingRate = samplingRate / 2000
                val n = fft.size
                magnitudes = DoubleArray(n / 2 + 1)
                val phases = FloatArray(n / 2 + 1)
                magnitudes!![0] = abs(fft[0].toDouble()) // DC
                magnitudes!![n / 2] = abs(fft[1].toDouble())// Nyquist
                phases[n / 2] = 0f
                phases[0] = phases[n / 2]
                for (k in 1 until n / 2) {
                    val i = k * 2
                    var magnitude = 0.0
                    magnitude = Math.hypot(
                        fft[i].toDouble(),
                        fft[i + 1].toDouble()
                    ).toFloat().toDouble()
                    val dbValue = (20 * Math.log10(magnitude)).toInt()
                    magnitude = Math.round((dbValue * 8).toFloat()).toDouble()
                    magnitudes!![k] = magnitude
                    phases[k] = Math.atan2(
                        fft[i + 1].toDouble(),
                        fft[i].toDouble()
                    ).toFloat()
                    //magnitudes[k] = (float)Math.hypot(fft[i],fft[i + 1]);
                }
                invalidate()
            }
        }, Visualizer.getMaxCaptureRate() / 2, false, true)
        mVisualizer!!.enabled = true
    }
}
