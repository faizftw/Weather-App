package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText etCity;

    Button btGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCity = findViewById(R.id.inputCityEdit);
         btGet = findViewById(R.id.btGet);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etCity.getText().toString())){
                    etCity.setError("Required");
                }else {
                    String city = etCity.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), weather.class);
                    intent.putExtra("City",city);
                    startActivity(intent);
                }
            }
        });



    }
}