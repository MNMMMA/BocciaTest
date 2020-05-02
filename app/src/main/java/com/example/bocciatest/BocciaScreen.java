package com.example.bocciatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import java.util.Locale;
import java.util.Random;

public class BocciaScreen extends AppCompatActivity {

    private int bx,by;
    private TextToSpeech t1;
    private MediaPlayer mp = new MediaPlayer();
    Random rand = new Random();
    boolean flag = false;
    private Handler handler = new Handler();
    private double distance;
    private int angle;
    private long y;
    double Rad2Deg = 180.0 / Math.PI;
    float screenX,screenY;
    private int radius = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
FullScreencall();

        final Locale myLocale = new Locale("pt", "PT");

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(myLocale);
                }
            }
        });
       // setContentView(R.layout.activity_boccia_screen);
        setContentView(new MyView(this));


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;



    }


    public class MyView extends View {
        Paint paint = null;
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

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);

            paint.setColor(Color.RED);


            bx = rand.nextInt(x);
            by =  rand.nextInt(y);


            canvas.drawCircle(bx, by, radius, paint);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {


            int x = (int) event.getX();
            int y = (int) event.getY();

            if (event.getAction() != MotionEvent.ACTION_DOWN)
                return false;


                    if (Math.abs(x - bx) < radius && Math.abs(y - by) < radius) {

                        flag = true;

                        distance = Math.sqrt(Math.pow(bx, 2) + Math.pow(by, 2));

                        //mp.setVolume((float) (Math.cos(angle) +1)/2,(1-(float) Math.cos(angle))/2);

                        angle = Angle(bx,by);

                        String toSpeak = " A bola está aqui";

                        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                        return false;
                }else{

                        double dx = getH(bx-x);
                        double dy =  getW(by-y);


                        String dirH = "";
                        String dirV = "";
                        if(dx > 0){
                            dirH = "esquerda";

                        }else if(dx < 0){
                            dirH = "direita";
                            dx=dx*-1;
                        }else{
                            dirH = "";
                        }

                        if(dy > 0){
                            dirV = "acima";

                        }else if(dy < 0){
                            dirV = "baixo";
                            dy=dy*-1;
                        }else{
                            dirV = "";
                        }

                        String distx = String.format("%.2f",dx);
                        String disty = String.format("%.2f",dy);

                        String toSpeak = " A bola está á " + distx + " metros "+ dirH +" e está á "+ disty +" metros para "+ dirV;
                        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                    }

            return false;

        }
    }

    double getH(float x){

        return ((x*10.0) / screenY);
    }

    double getW(float y){
        return ((y*6.0)/screenX);
    }


    private int Angle( double x2,double y2)
    {
        return (int) (Math.atan2(y2,x2) * Rad2Deg);
    }

    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


}
