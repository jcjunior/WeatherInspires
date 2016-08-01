package br.com.josecarlos.weatherinspires.delegate;

import android.app.Application;

import java.util.List;

import br.com.josecarlos.weatherinspires.models.CityWeatherListVO;

/**
 * Created by jcjunior on 16/07/2016.
 */
public interface GetCityWeatherForecastDelegate {

    void manageGetCityWeatherForecastSuccess(List<CityWeatherListVO> cityWeatherForecastList);
    void managerCallbackError(Exception e);
    Application retrieveApplication();

}
