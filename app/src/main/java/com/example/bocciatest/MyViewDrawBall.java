package com.example.bocciatest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Random;

public class MyViewDrawBall extends MyView {

    public int sizeX,sizeY,bx, by,radius=50;
    Random rand = new Random();
    boolean flag;

    public MyViewDrawBall(Context context, int bx, int by, boolean flag) {
        super(context);
        this.bx = bx;
        this.by = by;
        this.flag = flag;
    }

    public MyViewDrawBall(Context context, int bx, int by, boolean flag,int sizeX,int sizeY) {
        super(context);
        this.bx = bx;
        this.by = by;
        this.flag = flag;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getWidth();
        int y = getHeight();

        paint.setColor(Color.RED);


        bx = rand.nextInt(x);  // versao alternativa construir bola com o tamanho do ecra este erro vai mudar com a posicao seria da bola
        by = rand.nextInt(y);

        canvas.drawCircle(bx, by, radius, paint);

    }

    public int getBx() {
        return bx;
    }

    public int getBy() {
        return by;
    }
}
