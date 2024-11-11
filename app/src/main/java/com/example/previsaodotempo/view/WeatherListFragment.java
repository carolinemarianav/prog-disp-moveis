package com.example.previsaodotempo.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.previsaodotempo.R;
import com.example.previsaodotempo.data.WeatherService;
import com.example.previsaodotempo.domain.WeatherCard;
import com.example.previsaodotempo.domain.WeatherResponseData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherListFragment extends Fragment {

    private RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;
    private WeatherViewModel weatherViewModel;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.hgbrasil.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    WeatherService weatherApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_weather);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(weatherAdapter);

        Log.i("info", "onCreateView called");

        weatherApi = retrofit.create(WeatherService.class);

        weatherAdapter = new WeatherAdapter(new ArrayList<>());
        recyclerView.setAdapter(weatherAdapter);

        weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

        weatherViewModel.getWeatherList().observe(getViewLifecycleOwner(), weatherCards -> {
            weatherAdapter.updateWeatherList(weatherCards); // Atualiza a lista no Adapter
        });

        return view;
    }

    public void fetchWeatherByWOEID(int woeid, Consumer<WeatherCard> callback) {

        Log.i("info", "fetchWeatherByWOEID called");

        if (getActivity() == null) {
            return;
        }

        Call<WeatherResponseData> call = weatherApi.getWeatherData(woeid);

        getActivity().runOnUiThread(() -> {
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<WeatherResponseData> call, Response<WeatherResponseData> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.i("info", "result API " + response.body().results.toString());
                        WeatherResponseData.Results results = response.body().results;
                        var card = new WeatherCard(
                                results.city,
                                results.description,
                                results.date,
                                results.temp,
                                woeid
                        );

                        if (callback != null) {
                            callback.accept(card);
                        }

                        Log.i("info", "card: " + card.toString());

                        weatherViewModel.addWeatherCard(card);
                    } else {
                        Log.e("WeatherListFragment", "Erro na resposta: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<WeatherResponseData> call, Throwable t) {

                    Log.i("info", "onFailure called");

                    Log.e("WeatherListFragment", "Falha na requisição", t);
                }
            });
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        Log.i("info2", "list: " + weatherViewModel.getWeatherList().getValue());
    }
}


