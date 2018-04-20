package com.jcs.weatherinformation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class WeatherDetailActivity extends AppCompatActivity {

    private TextView high_temp, low_temp, temp, humidity_level, wind_speed, curr_date;
    private ImageView weather_icon;

    Bundle extras;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detailed);

        extras = getIntent().getExtras();
        high_temp = (TextView) findViewById(R.id.high_temp);
        low_temp = (TextView) findViewById(R.id.low_temp);
        temp = (TextView) findViewById(R.id.txt_temp);
        humidity_level = (TextView) findViewById(R.id.humidity_txt);
        wind_speed = (TextView) findViewById(R.id.speed_txt);
        weather_icon = (ImageView) findViewById(R.id.img_weather);
        curr_date = (TextView) findViewById(R.id.curr_date);
        weather_icon = (ImageView) findViewById(R.id.img_weather);

        high_temp.setText(Double.toString(extras.getDouble("high_temp")));
        low_temp.setText(Double.toString(extras.getDouble("low_temp")));
        temp.setText(Double.toString(extras.getDouble("temp")));
        humidity_level.setText(extras.getInt("humid_level") + " %");
        wind_speed.setText(Double.toString(extras.getDouble("wind")) + " km/h");
        curr_date.setText(extras.getString("date"));



        Picasso.with(this).load(extras.getString("weather_icon")).into(weather_icon);
    }
}
