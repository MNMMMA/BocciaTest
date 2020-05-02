package com.example.bocciatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.Locale;
import java.util.Random;

public class TestBepActivity extends AppCompatActivity {


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new TestBepActivity.MyView(this));

        final Locale myLocale = new Locale("pt", "PT");

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
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

    public class MyView extends View {
        Paint paint = null;
        long rate;

        public MyView(Context context) {
            super(context);
            paint = new Paint();
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
                distance = Math.sqrt(Math.pow(getW(y), 2));
                flag = true;
                //mp.setVolume((float) (Math.cos(angle) +1)/2,(1-(float) Math.cos(angle))/2);
                angle = Angle(bx,by);

                rate = (long) bep_rate(distance);

                runnable.run();
                return false;
        }
    }

    double getH(float y){

        return ((y*10.0) /height);
    }

    double getW(float x){
        return ((x*6.0)/width);
    }


    private int Angle( double x2,double y2)
    {
        return (int) (Math.atan2(y2,x2) * Rad2Deg);
    }

}
