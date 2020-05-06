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

public class TestBepActivity extends AppCompatActivity {


    private MediaPlayer mp = new MediaPlayer();
    boolean flag = false;
    private Handler handler = new Handler();
    private double distance;
    private int angle;
    double Rad2Deg = 180.0 / Math.PI;
    private int radius = 50, width, height;
    private long rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FullScreencall();
        setContentView(R.layout.activity_test_bep);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

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

    private double bep_rate(double x) {
        return 0.5 * x + 1;
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

    double getH(float y) {
        return ((y * 10.0) / height);
    }

    double getW(float x) {
        return ((x * 6.0) / width);
    }


    private int Angle(double x2, double y2) {
        return (int) (Math.atan2(y2, x2) * Rad2Deg);
    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

}
