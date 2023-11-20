package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class weather extends AppCompatActivity {
    TextView tvresult,title,Wname,Ftemp,Htemp,wdesc;
    ImageView wIcon;

    class getWeather extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while ((line = reader.readLine())!=null){
                    result.append(line).append("\n");

                }
                return result.toString();
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {
                title.setText("City not found");
                tvresult.setText("");
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject mainObject = jsonObject.getJSONObject("main");
                JSONObject weatherObject = jsonObject.getJSONArray("weather").getJSONObject(0);

                double temperature = mainObject.getDouble("temp");
                double feelsLike = mainObject.getDouble("feels_like");
                int humidity = mainObject.getInt("humidity");
                String weather = weatherObject.getString("main");
                String weatherdesc = weatherObject.getString("description");

                String weatherInfo = temperature + "°C";
                String humid = humidity +  "%";
                String feels = feelsLike +  "°C";





                tvresult.setText(weatherInfo);
                Htemp.setText(humid);
                Ftemp.setText(feels);
                Wname.setText(weather);
                wdesc.setText(weatherdesc);


                if (weather.equals("Thunderstorm")) {
                    wIcon.setImageResource(R.drawable.thunderstorm_transformed);
                } else if (weather.equals("Drizzle")) {
                    wIcon.setImageResource(R.drawable.shower_rain_transformed);
                } else if (weather.equals("Rain")) {
                    wIcon.setImageResource(R.drawable.rain_transformed);
                } else if (weather.equals("Snow")) {
                    wIcon.setImageResource(R.drawable.snow_transformed);
                }else if (weather.equals("Clear")) {
                    wIcon.setImageResource(R.drawable.sunny__1_);
                }else if (weather.equals("Clouds")) {
                    wIcon.setImageResource(R.drawable.broken_cloud_transformed);
                } else if (weather.equals("Mist") || weather.equals("Smoke") || weather.equals("Haze") || weather.equals("Dust") || weather.equals("Fog") || weather.equals("Sand") ||  weather.equals("Ash") || weather.equals("Squall") || weather.equals("Tornado")) {
                    wIcon.setImageResource(R.drawable.mist_transformed);
                } else {
                    wIcon.setImageResource(R.drawable.sunny__1_);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        tvresult = findViewById(R.id.tvResult);
        title = findViewById(R.id.title1);
        Wname = findViewById(R.id.WeatherName);
        Ftemp = findViewById(R.id.feelsTemp);
        Htemp = findViewById(R.id.humidTemp);
        wIcon = findViewById(R.id.Wicon);
        wdesc = findViewById(R.id.Wdesc);




        String city = getIntent().getStringExtra("City");

        title.setText("Weather in " + city + " City");

        String url = "https://openweathermap.org/data/2.5/weather?q=" + city + "&appid=439d4b804bc8187953eb36d2a8c26a02";
        getWeather task = new getWeather();
        task.execute(url);
    }
}