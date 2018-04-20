package com.jcs.weatherinformation.APIs;

import com.jcs.weatherinformation.Model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI  {

    @GET("/data/2.5/forecast/")
    Call<WeatherModel> getWeatherInfo(@Query("q") String place,
                                      @Query("units") String units,
                                      @Query("appid") String appid);

}
