package br.com.josecarlos.weatherinspires.view.fragments;

import android.app.Application;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import br.com.josecarlos.weatherinspires.R;
import br.com.josecarlos.weatherinspires.broadcast.GetTodaysCityWeatherReceiver;
import br.com.josecarlos.weatherinspires.delegate.GetTodaysCityWeatherDelegate;
import br.com.josecarlos.weatherinspires.models.CityWeatherListVO;
import br.com.josecarlos.weatherinspires.network.CityWeatherRest;
import br.com.josecarlos.weatherinspires.utils.Constants;
import br.com.josecarlos.weatherinspires.view.activity.WeatherDetailsActivity_;
import br.com.josecarlos.weatherinspires.view.adapter.WeatherListAdapter;

/**
 * Created by jcjunior on 14/07/2016.
 */

@EFragment(R.layout.fragment_city_weather_list)
public class CityWeatherListFragment extends Fragment implements GetTodaysCityWeatherDelegate {

    private static final String TAG = CityWeatherListFragment.class.getSimpleName();

    @Bean
    CityWeatherRest cityWeatherRest;

    @Bean
    WeatherListAdapter weatherListAdapter;

    @ViewById
    ListView weatherCitiesList;

    @FragmentArg(Constants.CITY_NAMES)
    String[] citiesNames;

    @FragmentArg(Constants.UNIT)
    String unit;

    @InstanceState
    ArrayList<CityWeatherListVO> citiesWeather;

    private GetTodaysCityWeatherReceiver getTodaysCityWeatherReceiver;

    public CityWeatherListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTodaysCityWeatherReceiver = GetTodaysCityWeatherReceiver.registerObserver(this);
        setHasOptionsMenu(true);
    }


    public void onCreateView(){
        if (citiesWeather == null){
            getAllSavedCitiesWeather();
        } else {
            cleanAdapter();
            reloadAdaptersList();
        }
    }

    private void getAllSavedCitiesWeather() {

        if (citiesNames != null) {
            citiesWeather = new ArrayList<>();
            for (String cityName : citiesNames) {
                getTodaysCityWeather(cityName, unit);
            }
        }

    }

    void getTodaysCityWeather(String cityName, String unit){
        cityWeatherRest.getTodaysCityWeather(cityName, unit);
    }

    private void cleanAdapter() {
        if (weatherListAdapter != null){
            weatherListAdapter.clear();
            weatherListAdapter.notifyDataSetChanged();
        }
    }

    private void reloadAdaptersList() {
        weatherListAdapter.setWeathers(citiesWeather);
        weatherListAdapter.notifyDataSetChanged();
        weatherCitiesList.setAdapter(weatherListAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getTodaysCityWeatherReceiver.unregisterObserver(retrieveApplication());
    }

    @ItemClick
    void weatherCitiesListItemClicked(CityWeatherListVO weatherListVO){

        if (weatherListVO != null && weatherListVO.getCityName() != null) {

            Intent intent = WeatherDetailsActivity_.intent(getActivity()).get();

            for (String name : citiesNames) {
                if (weatherListVO.getCityName().equalsIgnoreCase(name)) {
                    weatherListVO.setSavedCity(true);
                }
            }

            intent.putExtra(Constants.CITY_WEATHER, weatherListVO);
            startActivity(intent);

        }

    }

    @Override
    public void manageGetTodaysCityWeatherSuccess(CityWeatherListVO cityWeatherListVO) {
        citiesWeather.add(cityWeatherListVO);
        reloadAdaptersList();
    }

    @Override
    public void managerCallbackError(Exception e) {
        Log.e(TAG, e.toString());
        Toast.makeText(getActivity(), "An error occurred on the service "+e, Toast.LENGTH_LONG).show();
    }

    @Override
    public Application retrieveApplication() {
        return getActivity().getApplication();
    }

    @Override
    public void onStop() {
        super.onStop();
        cleanAdapter();
    }

}