package com.jcs.weatherinformation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcs.weatherinformation.Model.List;
import com.jcs.weatherinformation.Model.Weather;
import com.jcs.weatherinformation.R;
import com.jcs.weatherinformation.WeatherDetailActivity;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WeatherInfoAdapter extends RecyclerView.Adapter<WeatherInfoAdapter.ViewHolder> {

    private ArrayList<List> mData;
    private ArrayList<Weather> mDataWeather;

    private Context context;
    private Activity activty;

    private String url = "http://openweathermap.org/img/w/";

    public WeatherInfoAdapter(Context context, Activity activty, ArrayList<List> mData) {
        this.mData = mData;
        this.context = context;
        this.activty = activty;
        mDataWeather = new ArrayList<Weather>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WeatherInfoAdapter.ViewHolder holder = null;
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_list, parent, false);
        holder = new ItemViewHolder(mView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ItemViewHolder itemholder = (ItemViewHolder) holder;

        for(int x = 0; x < mData.get(position).getWeather().size(); x++) {
            Picasso.with(context).load(url + mData.get(position).getWeather().get(x).getIcon() + ".png").into(itemholder.weather_icon);
            itemholder.weather_desc.setText(mData.get(position).getWeather().get(x).getDescription());
            itemholder.txt_weather.setText(mData.get(position).getWeather().get(x).getMain());
        }
        itemholder.info_temp.setText( String.valueOf(mData.get(position).getMain().getTemp()) );
        itemholder.weather_date.setText(mData.get(position).getDtTxt());

        itemholder.card_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WeatherDetailActivity.class);
                intent.putExtra("wind", mData.get(position).getWind().getSpeed());
                intent.putExtra("low_temp", mData.get(position).getMain().getTempMin());
                intent.putExtra("high_temp", mData.get(position).getMain().getTempMax());
                intent.putExtra("temp", mData.get(position).getMain().getTemp());
                intent.putExtra("humid_level", mData.get(position).getMain().getHumidity());
                intent.putExtra("date", mData.get(position).getDtTxt());
                for(int x = 0; x < mData.get(position).getWeather().size(); x++) {
                    intent.putExtra("weather_icon", url + mData.get(position).getWeather().get(x).getIcon() + ".png");
                }
                activty.startActivity(intent);
            }
        });
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View v) {
            super(v);
        }
    }

    private class ItemViewHolder extends WeatherInfoAdapter.ViewHolder {

        private ImageView weather_icon;
        private TextView txt_weather, weather_desc, info_temp, weather_date;
        private CardView card_container;


        ItemViewHolder(View v) {
            super(v);
            weather_icon = (ImageView) v.findViewById(R.id.weather_img);
            txt_weather = (TextView) v.findViewById(R.id.txt_weather);
            weather_desc = (TextView) v.findViewById(R.id.weather_desc);
            info_temp = (TextView) v.findViewById(R.id.weather_temp);
            card_container = (CardView) v.findViewById(R.id.card_container);
            weather_date = (TextView) v.findViewById(R.id.weather_date);
        }
    }

}
