package com.example.previsaodotempo.domain;

public class WeatherCard {
    private String city;
    private String description;
    private String date;
    private float temperature;

    private int woeid;

    public WeatherCard(String city, String description, String date, float temperature, int woeid) {
        this.city = city;
        this.description = description;
        this.date = date;
        this.temperature = temperature;
        this.woeid = woeid;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public float getTemperature() {
        return temperature;
    }
}
