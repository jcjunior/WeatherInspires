package br.com.josecarlos.weatherinspires.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jcjunior on 16/07/2016.
 */
public class CityWeatherForecast {

    @SerializedName("list")
    private List<CityWeather> weatherForecast;

    public List<CityWeather> getWeatherForecast() {
        return weatherForecast;
    }

    public void setWeatherForecast(List<CityWeather> weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    @Override
    public String toString() {
        return "CityWeatherForecast{" +
                "weatherForecast=" + weatherForecast +
                '}';
    }
}
