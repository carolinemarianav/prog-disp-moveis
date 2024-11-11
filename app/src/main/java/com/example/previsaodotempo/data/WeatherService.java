package com.example.previsaodotempo.data;


import com.example.previsaodotempo.domain.WeatherResponseData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("weather")
    Call<WeatherResponseData> getWeatherData(@Query("woeid") int woeid);
}
