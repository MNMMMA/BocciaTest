package com.example.bocciatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Locale;

public class TestTTSActivity extends AppCompatActivity {

    private TextToSpeech t1;
    float screenX,screenY;


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
        setContentView(new TestTTSActivity.MyView(this));


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;



    }


    public class MyView extends View {
        Paint paint = null;


        public MyView(Context context) {
            super(context);
            paint = new Paint();

        }

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {


            int x = (int) event.getX();
            int y = (int) event.getY();

            if (event.getAction() != MotionEvent.ACTION_DOWN)
                return false;


                String sx = String.format("%.2f",getW(x));

                String sy = String.format("%.2f",getH(y));

                String toSpeak = " O dedo está na posiçao horizontal "+ sx +" e está na posição vertical "+ sy;

                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                return false;


        }

    double getH(float y){

        return ((y*10.0) /screenY);
    }

    double getW(float x){
        return ((x*6.0)/screenX);
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
