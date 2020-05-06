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


public class BocciaSoundByte extends AppCompatActivity {

    private float bx, by;
    private TextToSpeech t1;
    private MediaPlayer mp = new MediaPlayer();
    Random rand = new Random();
    boolean flag = false;
    private Handler handler = new Handler();
    private double distance;
    private int angle;
    double Rad2Deg = 180.0 / Math.PI;
    private int radius = 50, width, height;
    private MyView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new MyView(this);
        setContentView(view);

        final Locale myLocale = new Locale("pt", "PT");

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(myLocale);
                }
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
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

    @Override
    public void onBackPressed() {
        view.stopRepeatingTask();
        mp.stop();
        super.onBackPressed();
    }


    public class MyView extends View {
        Paint paint;
        long rate;

        public MyView(Context context) {
            super(context);
            paint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int x = getWidth();
            int y = getHeight();
            int radius;
            radius = 50;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            paint.setColor(Color.RED);

            bx = rand.nextInt(x);
            by = rand.nextInt(y);

            canvas.drawCircle(bx, by, radius, paint);
        }

        private double bep_rate(double x) {
            return 0.5 * x + 1;
        }

        // TODO: consultar https://stackoverflow.com/questions/6242268/repeat-a-task-with-a-time-delay
        //  e implementar de forma mais correta o som repetido.

        public Runnable runnable = new Runnable() {
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

        public void stopRepeatingTask() {
            handler.removeCallbacks(runnable);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {


            int x = (int) event.getX();
            int y = (int) event.getY();

            if (event.getAction() != MotionEvent.ACTION_DOWN)
                return false;

            if (Math.abs(y - by) < radius) {
                mp.stop();
                flag = false;

                // TODO: substituir todas as strings hard-coded por referências a resources
                String toSpeak = "A bola está aí mesmo";

                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                return false;

            } else {
                mp = MediaPlayer.create(BocciaSoundByte.this, R.raw.bep);
                // distance = Math.sqrt(Math.pow(getW(by - y), 2));
                distance = getH(by - y);
                flag = true;
                angle = Angle(bx, by);
                //mp.setVolume((float) (Math.cos(angle) +1)/2,(1-(float) Math.cos(angle))/2);

                rate = (long) bep_rate(distance);

                runnable.run();
                return false;
            }

        }
    }

}




