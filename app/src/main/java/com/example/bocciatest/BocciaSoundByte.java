package com.example.bocciatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;


public class BocciaSoundByte extends BaseActivity {


    private MediaPlayer mp = new MediaPlayer();
    private Handler handler = new Handler();

    private int sizeX,sizeY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        sizeX = size.x;
        sizeY = size.y;


        view = new MyViewDrawBall(this, bx, by, flag,sizeX,sizeY);

        setContentView(view);
        mp = MediaPlayer.create(BocciaSoundByte.this, R.raw.bep);


        runnable.run();


        sound(getH(screenY - by));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    @Override
    public void onBackPressed() {
        stopRepeatingTask();
        mp.stop();
        super.onBackPressed();
    }


    // TODO: consultar https://stackoverflow.com/questions/6242268/repeat-a-task-with-a-time-delay
    //  e implementar de forma mais correta o som repetido.

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (flag) {
                mp.setVolume((float) 0.1, (float) 0.9);
                mp.start();
            }
            handler.postDelayed(runnable, rate * 300);
        }
    };

    public void stopRepeatingTask() {
        handler.removeCallbacks(runnable);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int x = (int) event.getX();
        int y = (int) event.getY();

        bx = view.bx;
        by = view.by;

        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }

        if (Math.abs(y - by) < radius) {
            flag = false;

            Toast.makeText(getApplicationContext(), A_BOLA_ESTÁ_AÍ_MESMO, Toast.LENGTH_SHORT).show();
            t1.speak(A_BOLA_ESTÁ_AÍ_MESMO, TextToSpeech.QUEUE_FLUSH, null);

            return false;

        } else {
            double dy = Math.sqrt(Math.pow((y - by), 2));

            String speak = "y do dedo" + y + " y da bola " + by;

            Toast.makeText(getApplicationContext(), speak, Toast.LENGTH_SHORT).show();

            sound(getH((float) dy));
        }

        return false;
    }
}




