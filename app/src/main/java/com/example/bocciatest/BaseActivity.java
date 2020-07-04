package com.example.bocciatest;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Display;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bocciatest.campo.Campo;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    public Campo campo;
    public int radius = 50;
    protected MyViewDrawBall view;
    protected double distance;
    protected int angle;
    double Rad2Deg = 180.0 / Math.PI;
    boolean flag = true;
    protected TextToSpeech t1;
    public long rate;
    public static final String A_BOLA_ESTÁ_AÍ_MESMO = "A bola está aí mesmo";
    public static final String O_DEDO_ESTÁ_À_1_F_METROS_CENTIMETROS_NA_HORIZONTAL_E_1_F_METROS_CENTIMETROS_NA_VERTICAL = "O dedo está à %.1f metros centimetros na horizontal e %.1f metros centimetros na vertical";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        FullScreencall();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        campo = new Campo(size.x, size.y);

        final Locale myLocale = new Locale("pt", "PT");

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(myLocale);
                }
            }
        });

    }

    protected static double bep_rate(double x) {
        return 0.5 * x + 1;
    }


    protected int Angle(double x2, double y2) {
        return (int) (Math.atan2(y2, x2) * Rad2Deg);
    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void sound(double y) {

        // distance = Math.sqrt(Math.pow(getW(by - y), 2));

        flag = true;
        //angle = Angle(bx, by);
        //mp.setVolume((float) (Math.cos(angle) +1)/2,(1-(float) Math.cos(angle))/2);
        rate = (long) bep_rate(y);

    }
}
