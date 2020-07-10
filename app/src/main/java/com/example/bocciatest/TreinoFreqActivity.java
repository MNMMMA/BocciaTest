package com.example.bocciatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.bocciatest.campo.PontoCampo;
import com.example.bocciatest.campo.PontoEcra;

public class TreinoFreqActivity extends BaseActivity {

    private Handler handler = new Handler();
    private int duration = 3; // seconds
    private int sampleRate = 8000;
    private int numSamples = duration * sampleRate;
    private double[] sample = new double[numSamples];
    private double freqOfTone = 440; // hz
    boolean oFlag = false;
    private AudioTrack audioTrack;

    private final byte generatedSnd[] = new byte[2 * numSamples];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyCanvasView(this));


    }


    @Override
    protected void onResume() {
        super.onResume();
        genTone();
    }


    public void genTone() {
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (double dVal : sample) {
            // scale to maximum amplitude
            short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);

            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(runnable);
        super.onBackPressed();
    }


    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (audioTrack != null) {
                audioTrack.release();
            }

            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, numSamples,
                    AudioTrack.MODE_STREAM);
            audioTrack.play();
            if(flag){
                audioTrack.write(generatedSnd, 0, generatedSnd.length);
            }

            handler.postDelayed(runnable, rate * 300);
        }
    };

    private class MyCanvasView extends MyView {

        public PontoCampo posicaoCampo;

        public MyCanvasView(Context context) {
            super(context);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(resizedBitmap,-50,50,null);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                return false;
            }

            int x = (int) event.getX();
            int y = (int) event.getY();

            flag = false;
            int realY = (int) (campo.maxEcraY - y);
            int realX = (int) (campo.maxEcraX - x);

            PontoCampo ponto = campo.converter(new PontoEcra(realX, realY));


            runnable.run();

            double nRate = Math.sqrt(Math.pow(ponto.y, 2));
            freqOfTone = Math.abs(Math.log(nRate/100.0))*100 ; // depois inverter

           // distance = Math.sqrt(Math.pow(ponto.x, 2) + Math.pow(ponto.y, 2));

            sound(nRate);

            genTone();

            return false;

    }

}

}
