package com.example.bocciatest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class BocciaScreen extends AppCompatActivity {
    private int mActivePointerId;
    private int bx =1020,by=540;
    private double trueX,trueY;
    private TextToSpeech t1;
    private TextView pointField;
    private MediaPlayer mp = new MediaPlayer();
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boccia_screen);

        bx = rand.nextInt(2040) + 1;
        by = rand.nextInt(1080) + 1;

        trueX = getH();
        trueY = getW();

        final Locale myLocale = new Locale("pt", "PT");

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(myLocale);
                }
            }
        });


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //int height = pointField.getLineHeight();
       // int width = pointField.getWidth();

        //String toSpeak = " heigth " +height+  " Width " + width;
        //Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }

        if ((bx+50< x || bx-50 > x) &&(by+50 <y ||by-50>y)){
           // mp =  MediaPlayer.create(BocciaScreen.this,R.raw.bep);
           // mp.start();

            String toSpeak = " A bla esta na posição largura " + trueX +  " e comprimento " + trueY;
            Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            return false;


        }

        return false;

    }

    double getH(){

        return ((bx * 12.5) / 2040);
    }

    double getW(){
        return ((by*6.0)/1020);
    }
}
