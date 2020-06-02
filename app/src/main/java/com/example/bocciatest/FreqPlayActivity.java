package com.example.bocciatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.bocciatest.campo.PontoCampo;

public class FreqPlayActivity extends BaseActivity {


    private Handler handler = new Handler();


    private int duration = 1; // seconds
    private int sampleRate = 5000;
    private int numSamples = duration * sampleRate;
    private double sample[] = new double[numSamples];
    private double freqOfTone; // hz
    public AudioTrack audioTrack;
    private final byte generatedSnd[] = new byte[2 * numSamples];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyCanvasView(this));
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, numSamples, AudioTrack.MODE_STREAM);
    }


    @Override
    protected void onResume() {
        super.onResume();

        runnable.run();
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

    public void playSound() {

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, numSamples, AudioTrack.MODE_STREAM);

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

            if (flag) {
                genTone();
                playSound();
            }
            handler.postDelayed(runnable, rate * 300);
        }
    };


    private class MyCanvasView extends MyViewDrawBall {

        public MyCanvasView(Context context) {
            super(context, FreqPlayActivity.this.campo);


        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            sound(posicaoCampo.y);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                return false;
            }

            int x = (int) event.getX();
            int y = (int) event.getY();

            PontoCampo eventFieldPoint = campo.converter(x, y);
            double dx = posicaoCampo.x - eventFieldPoint.x;
            double dy = posicaoCampo.y - eventFieldPoint.y;
            double tolerancia = 0.1;

            if (Math.abs(dy) < tolerancia) {
                flag = false;

                Toast.makeText(getApplicationContext(), A_BOLA_ESTÁ_AÍ_MESMO, Toast.LENGTH_SHORT).show();
                t1.speak(A_BOLA_ESTÁ_AÍ_MESMO, TextToSpeech.QUEUE_FLUSH, null);

                return false;

            } else {

                Double nRate = Math.sqrt(Math.pow(dy, 2));
                freqOfTone =  1000.0/nRate; // depois inverter
                sound(nRate);

            }

            return false;
        }

    }

}
