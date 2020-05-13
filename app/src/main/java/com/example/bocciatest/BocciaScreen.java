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

public class BocciaScreen extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MyViewDrawBall view = new MyViewDrawBall(this, bx, by, flag);

        // setContentView(R.layout.activity_boccia_screen);
        setContentView(view);

        bx = view.bx;
        by = view.by;
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

            angle = Angle(bx, by);


            Toast.makeText(getApplicationContext(), A_BOLA_ESTÁ_AÍ_MESMO, Toast.LENGTH_SHORT).show();
            t1.speak(A_BOLA_ESTÁ_AÍ_MESMO, TextToSpeech.QUEUE_FLUSH, null);

            return false;
        } else {

            double dx = getH(bx - x);
            double dy = getW(by - y);


            String dirH = "";
            String dirV = "";

            // TODO: substituir todas as strings hard-coded por referências a resources
            if (dx > 0) {
                dirH = DIREITA;
            } else if (dx < 0) {
                dirH = ESQUERDA;
                dx = -dx;
            }
            if (dy > 0) {
                dirV = BAIXO;

            } else if (dy < 0) {
                dirV = CIMA;
                dy = -dy;
            }

            String toSpeak = String.format(
                    A_BOLA_ESTÁ_HÁ_2_F_METROS_CENTIMETROS_HÁ_S_E_2_F_METROS_CENTIMETROS_PARA_S,
                    dx, dirH,
                    dy, dirV
            );

            // TODO: criar mensagens diferenciadas para os casos:
            //  dx == 0, dy != 0
            //  dx != 0, dy == 0

            Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }

        return false;

    }

}