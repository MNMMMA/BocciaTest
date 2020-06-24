package com.example.bocciatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.bocciatest.campo.PontoCampo;
import com.example.bocciatest.campo.PontoEcra;

import java.util.Locale;
import java.util.Random;

public class TestBepActivity extends BaseActivity {


    private MediaPlayer mp = new MediaPlayer();
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FullScreencall();
        setContentView(new MyCanvasView(this));
        mp = MediaPlayer.create(TestBepActivity.this, R.raw.bep);

        runnable.run();
    }


    public void stopRepeatingTask() {
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onBackPressed() {
        stopRepeatingTask();
        mp.stop();
        super.onBackPressed();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (flag) {
                //mp.setVolume((float) 0.1, (float) 0.9);
                mp.start();
            }
            handler.postDelayed(runnable, rate * 400);
        }
    };

    private class MyCanvasView extends MyView {

        public MyCanvasView(Context context) {

            super(context);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {


            int x = (int) event.getX();
            int y = (int) event.getY();

            if (event.getAction() != MotionEvent.ACTION_DOWN)
                return false;


            flag = false;
            int realX = (int) (campo.maxEcraY - y);
            int realY = (int) (campo.maxEcraX - x);

            PontoCampo ponto = campo.converter(new PontoEcra(realX, realY));

            // TODO: implementar com X e Y
            // distance = Math.sqrt(Math.pow(getH(x), 2));
            //distance = getH(y); // TODO: remover esta implementação provisória

            double distance = Math.sqrt(Math.pow(Math.abs(ponto.x), 2) + Math.pow(Math.abs(ponto.y), 2));

            angle = (int) (180.0 * Math.atan2(ponto.y,ponto.x) / Math.PI);

            mp.setVolume((float) (Math.cos(angle) +1)/2,(1-(float) Math.cos(angle))/2);
            sound(distance);

            return false;
        }
    }

}
