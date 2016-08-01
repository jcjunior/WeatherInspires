package br.com.josecarlos.weatherinspires.delegate;

import android.app.Application;

import br.com.josecarlos.weatherinspires.models.CityWeatherListVO;

/**
 * Created by jcjunior on 16/07/2016.
 */
public interface GetTodaysCityWeatherDelegate {

    void manageGetTodaysCityWeatherSuccess(CityWeatherListVO cityWeatherListVO);
    void managerCallbackError(Exception e);
    Application retrieveApplication();

}
