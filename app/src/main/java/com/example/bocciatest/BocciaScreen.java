package com.example.bocciatest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
    private TextView pointField,bText;
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
        setContentView(R.layout.activity_boccia_screen);
        pointField = findViewById(R.id.textView6);
        bText = findViewById(R.id.posclick);
        bx = rand.nextInt(1080) ;
        by = rand.nextInt(2040-250)+250 ;

        trueX = getW();
        trueY = getH();

        String textS = "x " + bx +" y " + by;



        pointField.setText(textS);

         distance = Math.sqrt(Math.pow((trueX),2)+Math.pow((trueY),2));


         angle = Angle(0,0,trueX,trueY);
        //distance = Math.round(distance);
        // int trueDist = (int) distance;
        String distS = String.format("%.2f", distance);

        y = (long) bep_rate(distance);

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

    }

    double getH(){

        return ((2040-by-250.0) / 2040)*10;
    }

    double getW(){
        return ((bx*6.0)/1080);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(!flag){
                return;
            }
            mp.start();
            handler.postDelayed(runnable, y*300);
        }
    };
    private int Angle( double x1  , double y1 , double x2,double y2)
    {
        return (int) (Math.atan(y2-y1/x2-x1) * Rad2Deg);
    }

    private double bep_rate(double x){
        return 0.5*x+1;
    }
}
