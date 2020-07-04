package com.example.bocciatest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

class MyView extends View {
    Paint paint;
    Bitmap board;
    Rect rect;
    RectF rectF;

    public MyView(Context context) {
        super(context);
        paint = new Paint();
        board = BitmapFactory.decodeResource(getResources(),R.drawable.field1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);


        canvas.drawBitmap(board,0,0,null);
    }

}
