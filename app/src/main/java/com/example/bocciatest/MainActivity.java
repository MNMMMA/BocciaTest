package com.example.bocciatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.util.Locale;

import static java.lang.Math.atan2;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech t1;
    private Button btnSend,btnBep,btnMot;
    private EditText inputXCurLoc ,inputYCurLoc,inputXBallLoc,inputYBallLoc;
    double Rad2Deg = 180.0 / Math.PI;
    double Deg2Rad = Math.PI / 180.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputXCurLoc = findViewById(R.id.currentPx);
        inputYCurLoc = findViewById(R.id.currentPy);
        inputXBallLoc = findViewById(R.id.ballPx);
        inputYBallLoc = findViewById(R.id.ballPy);
        btnMot = findViewById(R.id.button3);
        btnSend = findViewById(R.id.button);
        btnBep = findViewById(R.id.button2);
        final Locale myLocale = new Locale("pt", "PT");

      t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(myLocale);
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double x1  = Double.parseDouble(inputXCurLoc.getText().toString());
                double y1 = Double.parseDouble(inputYCurLoc.getText().toString());
                double x2 = Double.parseDouble(inputXBallLoc.getText().toString());
                double y2 = Double.parseDouble(inputYBallLoc.getText().toString());
                double distance = Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2));


               int angle = Angle(x1,y1,x2,y2);
                //distance = Math.round(distance);
               // int trueDist = (int) distance;
                String distS = String.format("%.2f", distance);
               String toSpeak = "Distância até a bola é "+ distS +" metros, o ângulo  é "+ angle + " graus";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

            }
        });

        btnBep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BocciaSoundByte.class));
            }
        });

        btnMot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BocciaScreen.class));
            }
        });

    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    private int Angle( double x1  , double y1 , double x2,double y2)
    {
        return (int) (Math.atan(y2-y1/x2-x1) * Rad2Deg);
    }


}
