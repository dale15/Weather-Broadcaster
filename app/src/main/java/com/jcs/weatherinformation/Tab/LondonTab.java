package com.jcs.weatherinformation.Tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jcs.weatherinformation.APIs.WeatherAPI;
import com.jcs.weatherinformation.Adapter.WeatherInfoAdapter;
import com.jcs.weatherinformation.Model.List;
import com.jcs.weatherinformation.Model.WeatherModel;
import com.jcs.weatherinformation.R;
import com.jcs.weatherinformation.Utilities.RetrofitServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LondonTab extends Fragment {

    RecyclerView mWeatherContainer;
    WeatherInfoAdapter mWeatherInfoAdapter;

    WeatherAPI weatherAPI;

    ArrayList<List> dataModel = new ArrayList<List>();

    ProgressBar pBar;

    private void setWeatherInformation() {
        Call<WeatherModel> getWeatherInfo = weatherAPI.getWeatherInfo("London", "metric", "e6df605f86f15aab601853afee481841");
        getWeatherInfo.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if(response.isSuccessful()) {
                    pBar.setVisibility(View.GONE);
                    Log.d("GetList", String.valueOf(response.body().getList().size()));

                    for(int x = 0; x < response.body().getList().size(); x++) {
                        List weatherList = new List();
                        weatherList.setMain(response.body().getList().get(x).getMain());
                        weatherList.setDt(response.body().getList().get(x).getDt());
                        weatherList.setWeather(response.body().getList().get(x).getWeather());
                        weatherList.setWind(response.body().getList().get(x).getWind());
                        weatherList.setDtTxt(response.body().getList().get(x).getDtTxt());

                        dataModel.add(weatherList);
                    }
                    mWeatherInfoAdapter = new WeatherInfoAdapter(getContext(), getActivity(), dataModel);
                    mWeatherContainer.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    mWeatherContainer.setAdapter(mWeatherInfoAdapter);
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Log.d("throwError", t.getLocalizedMessage());
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        weatherAPI = RetrofitServiceGenerator.createService(WeatherAPI.class);

        View view = inflater.inflate(R.layout.weather_info_fragment, container, false);
        mWeatherContainer = (RecyclerView) view.findViewById(R.id.rec_container);
        pBar = (ProgressBar) view.findViewById(R.id.p_bar_contain);

        setWeatherInformation();

        return view;

    }

}
