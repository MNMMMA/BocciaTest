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

import com.example.bocciatest.campo.PontoCampo;

import java.util.Locale;
import java.util.Random;


public class BocciaSoundByte extends BaseActivity {


    boolean oFlag = false;
    private MediaPlayer mp = new MediaPlayer();
    private Handler handler = new Handler();
    int angle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new MyCanvasView(this));
        mp = MediaPlayer.create(BocciaSoundByte.this, R.raw.bep);

        runnable.run();
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
            try {
                if (flag) {
                //    mp.setVolume((float) 0.1, (float) 0.9);
                    mp.start();
                }else{
                    mp.stop();
                }
            } finally {
                handler.postDelayed(runnable, rate * 400);
            }


        }
    };

    public void stopRepeatingTask() {
        handler.removeCallbacks(runnable);
    }


    private class MyCanvasView extends MyViewDrawBall {

        public MyCanvasView(Context context) {
            super(context, BocciaSoundByte.this.campo);

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
                handler.removeCallbacks(runnable);
                oFlag = true;
            } else {
                if (oFlag){
                    runnable.run();
                    oFlag = false;
                }
                double distance = Math.sqrt(Math.pow(Math.abs(dx), 2) + Math.pow(Math.abs(dy), 2));

                angle = (int) (180.0 * Math.atan2(dy,dx) / Math.PI);

                sound(distance);
                mp.setVolume((float) (Math.cos(angle) +1)/2,(1-(float) Math.cos(angle))/2);

            }

            return false;
        }

    }


}




