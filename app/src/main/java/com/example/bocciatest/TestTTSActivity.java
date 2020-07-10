package com.example.bocciatest;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.bocciatest.campo.PontoCampo;
import com.example.bocciatest.campo.PontoEcra;

public class TestTTSActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new MyCanvasView(this));

    }

    private class MyCanvasView extends MyView {

        public MyCanvasView(Context context) {

            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(resizedBitmap,-50,50,null);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {


            double x = event.getX();
            double y = event.getY();

            if (event.getAction() != MotionEvent.ACTION_DOWN)
                return false;

            // TODO: substituir todas as strings hard-coded por referências a resources

                 int realX = (int) (campo.maxEcraY - y);
                 int realY = (int) (campo.maxEcraX - x);

            PontoCampo ponto = campo.converter(new PontoEcra(realX,realY));

            String toSpeak = String.format(O_DEDO_ESTÁ_À_1_F_METROS_CENTIMETROS_NA_HORIZONTAL_E_1_F_METROS_CENTIMETROS_NA_VERTICAL, ponto.x, ponto.y);
            String cpInv= "Campo inválido";
            if(ponto.x < 0){
                t1.speak(cpInv, TextToSpeech.QUEUE_FLUSH, null);
                return false;
            }
             Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

            return false;

        }


    }
}
