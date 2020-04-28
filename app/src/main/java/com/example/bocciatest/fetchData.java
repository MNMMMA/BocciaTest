package com.example.bocciatest;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchData extends AsyncTask<Void,Void,Void> {
    public String data ="";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://5ea06e39eea7760016a91caf.mockapi.io/ball");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line="";

            while (line!=null){
                line =bufferedReader.readLine();
                data += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
