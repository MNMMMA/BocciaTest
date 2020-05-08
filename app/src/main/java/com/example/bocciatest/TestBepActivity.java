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

import java.util.Locale;
import java.util.Random;

public class TestBepActivity extends BaseActivity {


    private MediaPlayer mp = new MediaPlayer();
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FullScreencall();
        setContentView(R.layout.activity_test_bep);

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
            if (!flag) {
                return;
            }

            mp.setVolume((float) 0.1, (float) 0.9);
            mp.start();
            handler.postDelayed(runnable, rate * 300);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return false;

        mp = MediaPlayer.create(TestBepActivity.this, R.raw.bep);

        // TODO: implementar com X e Y
        // distance = Math.sqrt(Math.pow(getH(x), 2));
        distance = getH(y); // TODO: remover esta implementação provisória

        flag = true;
        angle = Angle(x, y);
        //mp.setVolume((float) (Math.cos(angle) +1)/2,(1-(float) Math.cos(angle))/2);

        rate = (long) bep_rate(distance);

        runnable.run();
        return false;
    }

}
