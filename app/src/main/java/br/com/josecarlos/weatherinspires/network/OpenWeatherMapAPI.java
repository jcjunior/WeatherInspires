package br.com.josecarlos.weatherinspires.network;

import br.com.josecarlos.weatherinspires.models.CityWeather;
import br.com.josecarlos.weatherinspires.models.CityWeatherForecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jcjunior on 15/07/2016.
 */
public interface OpenWeatherMapAPI {

    @GET("/data/2.5/weather")
    Call<CityWeather> getCityWeather(@Query("q") String cityName, @Query("units") String unit, @Query("APPID") String appId );

    @GET("/data/2.5/forecast")
    Call<CityWeatherForecast> getCityWeatherForecast(@Query("q") String cityName, @Query("units") String unit, @Query("APPID") String appId );

}
