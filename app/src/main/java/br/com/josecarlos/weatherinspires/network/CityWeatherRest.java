package br.com.josecarlos.weatherinspires.network;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import br.com.josecarlos.weatherinspires.BuildConfig;
import br.com.josecarlos.weatherinspires.broadcast.GetCityWeatherForecastReceiver;
import br.com.josecarlos.weatherinspires.broadcast.GetTodaysCityWeatherReceiver;
import br.com.josecarlos.weatherinspires.models.CityWeather;
import br.com.josecarlos.weatherinspires.models.CityWeatherForecast;
import br.com.josecarlos.weatherinspires.models.CityWeatherListVO;
import br.com.josecarlos.weatherinspires.utils.Constants;
import br.com.josecarlos.weatherinspires.utils.DateUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jcjunior on 16/07/2016.
 */

@EBean
public class CityWeatherRest {

    private static final String TAG = CityWeatherRest.class.getSimpleName();
    private static final String APPID = BuildConfig.OPEN_WEATHER_MAP_API_KEY;

    @RootContext
    Context context;

    OpenWeatherMapAPI openWeatherMapAPI;

    public CityWeatherRest() {

        openWeatherMapAPI = RestApiClient.getClient().create(OpenWeatherMapAPI.class);
    }

    public void getTodaysCityWeather(String cityName, String unit) {

        Call<CityWeather> call = openWeatherMapAPI.getCityWeather(cityName, unit, APPID);

        call.enqueue(new Callback<CityWeather>() {

            @Override
            public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {

                if (response != null && response.body() != null){
                    CityWeatherListVO cityWeatherListVO = parseResponseToCityWeather(response.body());

                    GetTodaysCityWeatherReceiver.notifyNewParametersReceived(context, cityWeatherListVO);

                    Log.i(TAG, call.request().toString());
                    Log.i(TAG, "On response getTodaysCityWeather ["+response.body().toString()+"]");
                }

            }

            @Override
            public void onFailure(Call<CityWeather> call, Throwable t) {

                GetTodaysCityWeatherReceiver.notifyErrorReceived(context, (Exception) t);

                Log.i(TAG, call.request().toString());
                Log.i(TAG, "On Failure getTodaysCityWeather", t);
            }
        });

    }

    public void getCityWeatherForecast(String cityName, String unit){

        Call<CityWeatherForecast> call = openWeatherMapAPI.getCityWeatherForecast(cityName, unit, APPID);

        call.enqueue(new Callback<CityWeatherForecast>() {

            @Override
            public void onResponse(Call<CityWeatherForecast> call, Response<CityWeatherForecast> response) {

                if (response != null
                        && response.body() != null
                        && response.body().getWeatherForecast() != null){

                    List<CityWeatherListVO> cityWeathersListVO = parseResponseToCityWeatherList(response.body());
                    GetCityWeatherForecastReceiver.notifyNewParametersReceived(context, cityWeathersListVO);
                    Log.i(TAG, call.request().toString());
                    Log.i(TAG, "On response getCityWeatherForecast ["+response.body().toString()+"]");
                }

            }

            @Override
            public void onFailure(Call<CityWeatherForecast> call, Throwable t) {
                GetCityWeatherForecastReceiver.notifyErrorReceived(context, (Exception) t);
                Log.i(TAG, call.request().toString());
                Log.i(TAG, "On Failure getCityWeatherForecast", t);
            }
        });

    }

    private CityWeatherListVO parseResponseToCityWeather(CityWeather cityWeather) {

        CityWeatherListVO w1 = new CityWeatherListVO();

        if (cityWeather != null) {

            if (cityWeather.getErrorMessage() == null) {

                w1.setCityName(cityWeather.getCityName());
                w1.setWeatherId(cityWeather.getWeathers().get(0).getId().toString());
                w1.setTemp(cityWeather.getMain().getTemp().toString());
                w1.setCurrentDate(DateUtil.formatDateLongToString(cityWeather.getDate()));
                w1.setWeatherDescription(cityWeather.getWeathers().get(0).getDescription());

            } else {
                w1.setErrorMessage(cityWeather.getErrorMessage());
            }

        }

        return w1;
    }

    private List<CityWeatherListVO> parseResponseToCityWeatherList(CityWeatherForecast cityWeatherForecast) {

        List<CityWeatherListVO> cityWeatherListVOs = new ArrayList<>();

        if (cityWeatherForecast != null) {

            for (CityWeather cityWeather : cityWeatherForecast.getWeatherForecast()) {

                if (cityWeatherListVOs.size() == 0 ||
                        actualDay(cityWeatherListVOs) != nextDay(cityWeather)) {

                    CityWeatherListVO w1 = new CityWeatherListVO();
                    w1.setCurrentDate(cityWeather.getDateText());
                    w1.setWeekDay(DateUtil.getWeekDayOfADate(cityWeather.getDateText(), Constants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
                    w1.setWeatherId(cityWeather.getWeathers().get(0).getId().toString());
                    w1.setMaxTemp(cityWeather.getMain().getTempMax().toString());
                    w1.setMinTemp(cityWeather.getMain().getTempMin().toString());

                    cityWeatherListVOs.add(w1);
                }


            }
        }

        return cityWeatherListVOs;
    }

    private int actualDay(List<CityWeatherListVO> cityWeatherListVOs){
        return DateUtil.getDayOfDate(getCurrentDate(cityWeatherListVOs), Constants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    private int nextDay(CityWeather cityWeather){
        return DateUtil.getDayOfDate(cityWeather.getDateText(), Constants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
    }


    private String getCurrentDate(List<CityWeatherListVO> cityWeatherListVOs){
        return cityWeatherListVOs.get(cityWeatherListVOs.size() - 1).getCurrentDate();
    }

}
