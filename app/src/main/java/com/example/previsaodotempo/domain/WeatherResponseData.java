package com.example.previsaodotempo.domain;

public class WeatherResponseData {
    public Results results;

    public static class Results {
        public String city;
        public String description;
        public float temp;
        public String date;
    }
}
