package com.example.bocciatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import static java.lang.Math.atan2;

public class BocciaSoundByte extends AppCompatActivity {

    private Button btnSend,btnTTs;
    private EditText inputXCurLoc ,inputYCurLoc,inputXBallLoc,inputYBallLoc;
    private MediaPlayer mp = new MediaPlayer();
    private Handler handler = new Handler();
    double Rad2Deg = 180.0 / Math.PI;
    long y;
    boolean flag =  false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar

        setContentView(R.layout.activity_boccia_sound_byte);

        inputXCurLoc = findViewById(R.id.currentPx);
        inputYCurLoc = findViewById(R.id.currentPy);
        inputXBallLoc = findViewById(R.id.ballPx);
        inputYBallLoc = findViewById(R.id.ballPy);
        btnTTs = findViewById(R.id.button4);

        btnSend = findViewById(R.id.button);

        btnTTs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BocciaSoundByte.this, MainActivity.class));
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag){
                    flag =true;

                double x1  = Double.parseDouble(inputXCurLoc.getText().toString());
                double y1 = Double.parseDouble(inputYCurLoc.getText().toString());
                double x2 = Double.parseDouble(inputXBallLoc.getText().toString());
                double y2 = Double.parseDouble(inputYBallLoc.getText().toString());
                double distance = Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2));


                //mp.setDataSource(this,R.raw.bep);

                distance = Math.round(distance);

                 y = (long) bep_rate(distance);

                String toSpeak = "y is "+ y;

                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();

                        mp =  MediaPlayer.create(BocciaSoundByte.this,R.raw.bep);
                        runnable.run();

                       // handler.postDelayed(runnable, y*300);
                }else{
                    flag = false;
                }




                    //mp.stop();




            }
        });

    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(!flag){
            return;
            }
            mp.start();
            handler.postDelayed(runnable, y*300);
        }
    };


    private double bep_rate(double x){
        return 0.5*x+1;
    }

    private int Angle( double x1  , double y1 , double x2,double y2)
    {
        return (int) (Math.atan(y2-y1/x2-x1) * Rad2Deg);
    }


}
