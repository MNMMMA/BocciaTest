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

public class TreinoFreqActivity extends BaseActivity {


    private Handler handler = new Handler();


    private final int duration = 1; // seconds
    private final int sampleRate = 5000;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private double freqOfTone; // hz

    private final byte generatedSnd[] = new byte[2 * numSamples];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyCanvasView(this));


    }


    @Override
    protected void onResume() {
        super.onResume();

        // Use a new tread as this can take a while
        final Thread thread = new Thread(runnable);
        thread.start();
    }


    public void genTone() {
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate / freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for ( double dVal : sample) {
            // scale to maximum amplitude
            short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }
    }

    public void playSound() {
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, numSamples,
                AudioTrack.MODE_STREAM);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
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


    // TODO: consultar https://stackoverflow.com/questions/6242268/repeat-a-task-with-a-time-delay
    //  e implementar de forma mais correta o som repetido.

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            genTone();
            if (flag) {
                playSound();
            }
            handler.postDelayed(runnable, rate * 300);
        }
    };


    private class MyCanvasView extends MyView {

        public MyCanvasView(Context context) {
            super(context);


        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                return false;
            }

            int x = (int) event.getX();
            int y = (int) event.getY();

            PontoCampo point = campo.converter(x, y);

            distance = Math.sqrt(Math.pow(point.x, 2) + Math.pow(point.y, 2));




            freqOfTone = distance * 100.0;
            sound(distance);


            return false;
        }

    }

}
