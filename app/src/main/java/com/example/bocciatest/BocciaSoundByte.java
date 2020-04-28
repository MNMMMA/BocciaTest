package com.example.bocciatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bocciatest.model.Ball;
import com.example.bocciatest.model.Position;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import static java.lang.Math.atan2;

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
    private int dedo = 50, width, height;

    public Position position;
    public HashMap<Integer, Ball> balls = new HashMap<Integer, Ball>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final Locale myLocale = new Locale("pt", "PT");



        this.balls.put(1, new Ball(1, 2, 3, "Red"));
        this.balls.put(2, new Ball(2, 1, 6, "Black"));
        this.balls.put(3, new Ball(3, 3, 5, "Blue"));

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            bx = extras.getFloat("xpos");
            by = extras.getFloat("ypos");
        }

        position = new Position(1, bx, by);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(myLocale);
                }
            }
        });

        setContentView(new MyView(this));
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

           /* paint.setColor(Color.RED);

            bx = rand.nextInt(width);
            by =  rand.nextInt(height);*/
            Ball ball;

            for (Integer i : balls.keySet()) {
                ball = balls.get(i);
                paint.setColor(Color.parseColor(ball.getColour()));

                canvas.drawCircle(getHPixel(ball.getX()), getWPixel(ball.getY()), radius, paint);


            }


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

            rate = (long) bep_rate(distance);

            Ball ball;

            int x = (int) event.getX();
            int y = (int) event.getY();

            if (event.getAction() != MotionEvent.ACTION_DOWN)
                return false;
            if (!flag) {

                for (Integer i : balls.keySet()) {
                    ball = balls.get(i);

                    if (Math.abs(x - getHPixel(ball.getX())) < dedo && Math.abs(y - getWPixel(ball.getY())) < dedo) {

                        flag = true;
                        mp = MediaPlayer.create(BocciaSoundByte.this, R.raw.bep);

                        distance = Math.sqrt(Math.pow(((ball.getX())-position.getXpos()), 2) + Math.pow(((ball.getY())-position.getYpos()), 2));

                        //mp.setVolume((float) (Math.cos(angle) +1)/2,(1-(float) Math.cos(angle))/2);

                        angle = Angle(position.getXpos(), position.getYpos(), ball.getX(), ball.getY());

                        String distS = String.format("%.2f", distance);

                        rate = (long) bep_rate(distance);
                        String cor;

                        if(ball.getColour().equals("Red")){
                            cor = "vermelha";
                        }else if(ball.getColour().equals("Blue")){
                            cor = "azul";
                        }else{
                            cor = "branca";
                        }
                        String toSpeak = " A bola " + cor + " esta a " + distS + " metros e num angulo de " + angle;

                        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                        runnable.run();

                        return false;

                    }
                }

            } else {
                flag = false;
            }

            return false;

        }
    }


    double getH(float x) {

        return ((height - x) / height) * 10;
    }

    double getW(float y) {
        return ((y * 6.0) / width);
    }

    float getHPixel(float x) {

        return ((x * height) / 10);
    }

    float getWPixel(float y) {
        return ((y * width) / 6);
    }


    private int Angle(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        return (int) (Math.atan2(deltaY, deltaX) * Rad2Deg);
    }


}
