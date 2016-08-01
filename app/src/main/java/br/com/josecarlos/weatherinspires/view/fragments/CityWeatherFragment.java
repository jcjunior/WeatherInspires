package br.com.josecarlos.weatherinspires.view.fragments;

import android.app.Application;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.weathericonview.WeatherIconView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.com.josecarlos.weatherinspires.R;
import br.com.josecarlos.weatherinspires.broadcast.GetCityWeatherForecastReceiver;
import br.com.josecarlos.weatherinspires.broadcast.GetTodaysCityWeatherReceiver;
import br.com.josecarlos.weatherinspires.delegate.GetCityWeatherForecastDelegate;
import br.com.josecarlos.weatherinspires.delegate.GetTodaysCityWeatherDelegate;
import br.com.josecarlos.weatherinspires.models.CityWeatherListVO;
import br.com.josecarlos.weatherinspires.models.WeatherPreferences_;
import br.com.josecarlos.weatherinspires.network.CityWeatherRest;
import br.com.josecarlos.weatherinspires.utils.Constants;
import br.com.josecarlos.weatherinspires.utils.UtilView;
import br.com.josecarlos.weatherinspires.view.adapter.WeatherForecastAdapter;

/**
 * Created by jcjunior on 15/07/2016.
 */

@EFragment(R.layout.fragment_city_weather)
public class CityWeatherFragment extends Fragment implements GetTodaysCityWeatherDelegate, GetCityWeatherForecastDelegate {

    private static final String TAG = CityWeatherFragment.class.getSimpleName();

    @ViewById
    TextView txtErrorMessage;

    @ViewById
    TextView txtCityName;

    @ViewById
    TextView txtCurrentDate;

    @ViewById
    WeatherIconView imgWeather;

    @ViewById
    TextView txtCurrentTemperature;

    @ViewById
    TextView txtWeatherDescription;

    @ViewById
    ListView weatherForecastList;

    @ViewById
    ProgressBar loadingWeatherForecast;

    @ViewById
    SwipeRefreshLayout swipeRefreshForecastLayout;

    @FragmentArg(Constants.CITY_WEATHER)
    CityWeatherListVO citySelected;

    @FragmentArg(Constants.SEARCH_RESULT)
    String searchResult;

    @Pref
    WeatherPreferences_ weatherPreferences;

    @Bean
    WeatherForecastAdapter weatherForecastAdapter;

    @Bean
    CityWeatherRest cityWeatherRest;

    @Bean
    UtilView utilView;

    @ViewById
    FloatingActionButton addRemoveCityFAB;

    @FragmentArg(Constants.UNIT)
    String unit;

    private GetTodaysCityWeatherReceiver getTodaysCityWeatherReceiver;
    private GetCityWeatherForecastReceiver getCityWeatherForecastReceiver;
    private String cityName;

    public CityWeatherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getTodaysCityWeatherReceiver = GetTodaysCityWeatherReceiver.registerObserver(this);
        getCityWeatherForecastReceiver = GetCityWeatherForecastReceiver.registerObserver(this);
    }

    @AfterViews
    void onCreateView(){

        loadingWeatherForecast.setVisibility(View.VISIBLE);
        weatherForecastList.setVisibility(View.GONE);

        //get todays weather
        if (citySelected != null) {

            cityName = citySelected.getCityName();
            initTodaysWeather(citySelected);
            initFloatButton();

        } else if (searchResult != null){

            cityName = searchResult;
            getTodaysCityWeather(searchResult, unit);

        }

        //get forecast
        getCityWeatherForecast(cityName, unit);

        swipeRefreshForecastLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCityWeatherForecast(cityName, unit);
            }
        });


    }

    private void initFloatButton() {
        if (isSavedCityFromSelection()) {
            addRemoveCityFAB.setImageDrawable(getResources().getDrawable(R.drawable.abc_btn_rating_star_on_mtrl_alpha));
        } else {
            addRemoveCityFAB.setImageDrawable(getResources().getDrawable(R.drawable.abc_btn_rating_star_off_mtrl_alpha));
        }

        addRemoveCityFAB.setVisibility(View.VISIBLE);
    }

    void getTodaysCityWeather(String cityName, String unit){
        cityWeatherRest.getTodaysCityWeather(cityName, unit);
    }

    void getCityWeatherForecast(String cityName, String unit){
        cityWeatherRest.getCityWeatherForecast(cityName, unit);
    }

    @Click(R.id.addRemoveCityFAB)
    public void editCityPreference(){

        Set<String> cities = weatherPreferences.cities().get();

        //remove
        if (isSavedCityFromSelection()) {

            cities.remove(citySelected.getCityName());
            citySelected.setSavedCity(false);
            Toast.makeText(getActivity(), getString(R.string.title_city_removed_success, citySelected.getCityName()), Toast.LENGTH_LONG).show();

            //add
        } else {
            cities.add(citySelected.getCityName());
            citySelected.setSavedCity(true);
            Toast.makeText(getActivity(), getString(R.string.title_city_added_success, citySelected.getCityName()), Toast.LENGTH_LONG).show();
        }

        weatherPreferences.edit().cities().put(cities).apply();

        SharedPreferences app_preferences = getActivity().getSharedPreferences(Constants.APP_SHARED_PREFERENCES, getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putStringSet(Constants.CITY_NAMES, new TreeSet<>(weatherPreferences.cities().get()));
        editor.commit();

        initFloatButton();

    }

    private boolean isSavedCityFromSelection(){
        return (citySelected != null && citySelected.isSavedCity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getTodaysCityWeatherReceiver.unregisterObserver(retrieveApplication());
        getCityWeatherForecastReceiver.unregisterObserver(retrieveApplication());
    }


    private void initTodaysWeather(CityWeatherListVO cityWeatherListVO) {

        if (cityWeatherListVO != null && cityWeatherListVO.getCityName() != null) {

            txtCityName.setText(cityWeatherListVO.getCityName());
            txtCurrentDate.setText(cityWeatherListVO.getCurrentDate());
            imgWeather.setIconResource(utilView.getWeatherIconByName(cityWeatherListVO.getWeatherId()));
            imgWeather.setIconSize(60);
            txtCurrentTemperature.setText(utilView.addDegree(cityWeatherListVO.getTemp()));
            txtWeatherDescription.setText(cityWeatherListVO.getWeatherDescription());
        }
    }

    @Override
    public void manageGetTodaysCityWeatherSuccess(CityWeatherListVO cityWeatherListVO) {

        if (cityWeatherListVO != null){

            if (cityWeatherListVO.getErrorMessage() == null) {

                citySelected = cityWeatherListVO;
                initTodaysWeather(cityWeatherListVO);
                addRemoveCityFAB.setVisibility(View.VISIBLE);

            } else {
                addRemoveCityFAB.setVisibility(View.GONE);
                txtErrorMessage.setText(cityWeatherListVO.getErrorMessage());

            }

        }

    }

    @Override
    public void manageGetCityWeatherForecastSuccess(List<CityWeatherListVO> cityWeatherForecastList) {

        if (cityWeatherForecastList != null) {
            weatherForecastAdapter.setWeathers(cityWeatherForecastList);
            weatherForecastAdapter.notifyDataSetChanged();
            weatherForecastList.setAdapter(weatherForecastAdapter);

            weatherForecastList.setVisibility(View.VISIBLE);
            loadingWeatherForecast.setVisibility(View.GONE);
            swipeRefreshForecastLayout.setRefreshing(false);
        }

    }

    @Override
    public void managerCallbackError(Exception e) {

        Log.e(TAG, e.toString());

        swipeRefreshForecastLayout.setRefreshing(false);
        loadingWeatherForecast.setVisibility(View.GONE);
        weatherForecastList.setVisibility(View.VISIBLE);

        Toast.makeText(getActivity(), "An error occurred on the service "+e, Toast.LENGTH_LONG).show();
    }

    @Override
    public Application retrieveApplication() {
        return getActivity().getApplication();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (weatherForecastAdapter != null){
            weatherForecastAdapter.clear();
            weatherForecastAdapter.notifyDataSetChanged();
        }
    }


}
