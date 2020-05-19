package com.example.bocciatest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.bocciatest.campo.Campo;
import com.example.bocciatest.campo.PontoCampo;
import com.example.bocciatest.campo.PontoEcra;

import java.util.Random;

public class MyViewDrawBall extends MyView {

    public PontoCampo posicaoCampo;
    public int  radius=50;
    public Campo campo;
    Random rand = new Random();
    boolean flag;

    public MyViewDrawBall(Context context,Campo campo) {
        super(context);
        this.campo = campo;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getWidth();
        int y = getHeight();


        posicaoCampo = campo.getRandomPoint(); // talvez mais tarde por uma list na Classe campo

        paint.setColor(Color.RED);
        PontoEcra posicaoEcra = campo.converter(posicaoCampo);
        canvas.drawCircle(posicaoEcra.x, posicaoEcra.y, radius, paint);

    }

}
