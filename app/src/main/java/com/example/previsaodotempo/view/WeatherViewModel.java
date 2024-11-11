package com.example.previsaodotempo.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.previsaodotempo.domain.WeatherCard;

import java.util.ArrayList;
import java.util.List;

public class WeatherViewModel extends ViewModel {
    private final MutableLiveData<List<WeatherCard>> weatherList = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<WeatherCard>> getWeatherList() {
        return weatherList;
    }

    public void addWeatherCard(WeatherCard card) {
        List<WeatherCard> currentList = weatherList.getValue();
        if (currentList != null) {
            currentList.add(card);
            weatherList.setValue(currentList);
        }
    }
}
