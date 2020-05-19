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


import com.example.bocciatest.campo.PontoCampo;

import java.util.Locale;
import java.util.Random;

public class BocciaScreen extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_boccia_screen);

        setContentView(new MyCanvasView(this));

    }


    private class MyCanvasView extends MyViewDrawBall {

        public MyCanvasView(Context context) {

            super(context, BocciaScreen.this.campo);
        }

       /* @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
        }*/


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() != MotionEvent.ACTION_DOWN)
                return false;

            int x = (int) event.getX();
            int y = (int) event.getY();

            PontoCampo eventFieldPoint = campo.converter(x, y);
            double dx = posicaoCampo.x - eventFieldPoint.x;
            double dy = posicaoCampo.y - eventFieldPoint.y;

            double tolerancia = 0.1;
            String toSpeak;

            if (Math.abs(dx) < tolerancia && Math.abs(dy) < tolerancia) {

                toSpeak = A_BOLA_ESTÁ_AÍ_MESMO;

                return false;
            } else {

                String dirH;
                String dirV;

                // TODO: substituir todas as strings hard-coded por referências a resources
                if (dx > 0) {
                    dirH = DIREITA;
                } else if (dx < 0) {
                    dirH = ESQUERDA;
                    dx = -dx;
                } else {
                    dirH = "nessa coluna";
                }
                if (dy > 0) {
                    dirV = BAIXO;

                } else if (dy < 0) {
                    dirV = CIMA;
                    dy = -dy;
                } else {
                    dirV = "nessa linha";
                }

                toSpeak = String.format(
                        A_BOLA_ESTÁ_HÁ_2_F_METROS_CENTIMETROS_HÁ_S_E_2_F_METROS_CENTIMETROS_PARA_S,
                        dx, dirH,
                        dy, dirV
                );


            }


            Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

            return false;

        }


    }

}