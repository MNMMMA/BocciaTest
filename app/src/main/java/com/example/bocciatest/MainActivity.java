package com.example.bocciatest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import static android.os.Build.ID;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech t1;
    private Button btnSend,btnBep,btnMot;
    private EditText inputXCurLoc ,inputYCurLoc;
    public static TextView json;
    double Rad2Deg = 180.0 / Math.PI;
    double Deg2Rad = Math.PI / 180.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        String result = null;


        //json.setText(result);
        super.onCreate(savedInstanceState);
        json = findViewById(R.id.json);

        setContentView(R.layout.activity_main);



        inputXCurLoc = findViewById(R.id.currentPx);
        inputYCurLoc = findViewById(R.id.currentPy);
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

                fetchData proccess = new fetchData();

                proccess.execute();
                Toast.makeText(getApplicationContext(), proccess.data,Toast.LENGTH_SHORT).show();
            }
        });

        btnBep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BocciaSoundByte.class);
                intent.putExtra("xpos",inputXCurLoc.getText());
                intent.putExtra("ypos",inputYCurLoc.getText());
                startActivityForResult(intent,2);
                //startActivity(new Intent(MainActivity.this, BocciaSoundByte.class));
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


    public String getJSON(){

        try {
            URL url = new URL("https://5ea06e39eea7760016a91caf.mockapi.io/ball");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            return null;
        }
    }

}


