package com.example.bocciatest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class BocciaScreen extends AppCompatActivity {
    private int mActivePointerId;
    private int bx =1020,by=540;
    private double trueX,trueY;
    private TextToSpeech t1;
    private MediaPlayer mp = new MediaPlayer();
    Random rand = new Random();
    boolean flag = false;
    private Handler handler = new Handler();
    private int maxVolume = 50;
    private double distance;
    private int angle;
    private long y;
    double Rad2Deg = 180.0 / Math.PI;
    private int soundId,dedo = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Locale myLocale = new Locale("pt", "PT");

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(myLocale);
                }
            }
        });
        setContentView(R.layout.activity_boccia_screen);
        //setContentView(new MyView(this));

        final ImageButton buttonRed =  findViewById(R.id.redB);
        final ImageButton buttonBlue =  findViewById(R.id.blueB);
        final ImageButton buttonBlack =  findViewById(R.id.blackB);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;



        buttonRed.setX(0);
        buttonRed.setY(0);

        buttonBlack.setX(rand.nextInt(width));
        buttonBlack.setY(rand.nextInt(height));



        buttonBlue.setX(rand.nextInt(width));
        buttonBlue.setY(rand.nextInt(height));



        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag){
                    flag =true;
                    trueX = getW(buttonRed.getX());
                    trueY = getH(buttonRed.getY());
                    angle = Angle(0,0,trueX,trueY);



                    distance = Math.sqrt(Math.pow((trueX),2)+Math.pow((trueY),2));
                    String distS = String.format("%.2f", distance);

                    y = (long) bep_rate(distance);

                    mp =  MediaPlayer.create(BocciaScreen.this,R.raw.bep);


                    String toSpeak = " A bola vermelha esta a " + distS +  " metros e num angulo de " + angle;

                    Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                    runnable.run();

                }else{
                    flag = false;
                }

            }
        });

        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag){
                    flag =true;
                    trueX = getW(buttonBlue.getX());
                    trueY = getH(buttonBlue.getY());
                    angle = Angle(0,0,trueX,trueY);



                    distance = Math.sqrt(Math.pow((trueX),2)+Math.pow((trueY),2));
                    String distS = String.format("%.2f", distance);

                    y = (long) bep_rate(distance);

                    mp =  MediaPlayer.create(BocciaScreen.this,R.raw.bep);


                    String toSpeak = " A bola azul esta a " + distS +  " metros e num angulo de " + angle;

                    Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                    runnable.run();

                }else{
                    flag = false;
                }

            }
        });

        buttonBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag){
                    flag =true;


                    trueX = getW(buttonBlack.getX());
                    trueY = getH(buttonBlack.getY());
                    angle = Angle(0,0,trueX,trueY);


                    distance = Math.sqrt(Math.pow((trueX),2)+Math.pow((trueY),2));
                    String distS = String.format("%.2f", distance);

                    y = (long) bep_rate(distance);

                    mp =  MediaPlayer.create(BocciaScreen.this,R.raw.bep);


                    String toSpeak = " A bola preta esta a " + distS +  " metros e num angulo de " + angle;

                    Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                    t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                    runnable.run();

                }else{
                    flag = false;
                }

            }
        });



/*

         angle = Angle(0,0,trueX,trueY);
        //distance = Math.round(distance);
        // int trueDist = (int) distance;
        String distS = String.format("%.2f", distance);

        y = (long) bep_rate(distance);

*/

    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int x = (int)event.getX();
        int y = (int)event.getY();

        String posT = "x " + x +" y " + y;
        bText.setText(posT);

        if ( event.getAction()!= MotionEvent.ACTION_DOWN)
            return false;
        if (!flag) {

        if (Math.abs(x-bx) < dedo  && Math.abs(y-by) <dedo){

            flag = true;
          mp =  MediaPlayer.create(BocciaScreen.this,R.raw.bep);

            //mp.setVolume((float) (Math.cos(angle) +1)/2,(1-(float) Math.cos(angle))/2);



            String distS = String.format("%.2f", distance);
            String toSpeak = " A bola esta a " + distS +  " metros e num angulo de " + angle;

            Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

           // soundId = mp.load(BocciaScreen.this,R.raw.bep, 1);
            runnable.run();

            return false;

        }

        } else{
        flag = false;
    }

        return false;

    }*/

    double getH(float x){

        return ((2040-x) / 2040.0)*10;
    }

    double getW(float y){
        return ((y*6.0)/1080);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(!flag){
                return;
            }

            mp.setVolume((float)0.1, (float)0.9);
            mp.start();
            handler.postDelayed(runnable, y*300);
        }
    };
    private int Angle( double x1  , double y1 , double x2,double y2)
    {

        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        return (int) (Math.atan2(deltaY, deltaX) * Rad2Deg);
    }

    private double bep_rate(double x){
        return 0.5*x+1;
    }
}
