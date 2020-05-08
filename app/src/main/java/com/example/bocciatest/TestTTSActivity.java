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

public class TestTTSActivity extends BaseActivity {


    private TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.bocciatest.MyView myView = new com.example.bocciatest.MyView(this);
        setContentView(myView);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        double x = getW(event.getX());
        double y = getH(event.getY());

        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return false;

        // TODO: substituir todas as strings hard-coded por referências a resources

        double realX = getH(screenY) - y;
        double realY = getW(screenX) - x;

        String toSpeak = String.format(O_DEDO_ESTÁ_À_2_F_METROS_CENTIMETROS_NA_HORIZONTAL_E_2_F_METROS_CENTIMETROS_NA_VERTICAL, realX, realY);

        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

        return false;

    }
}
