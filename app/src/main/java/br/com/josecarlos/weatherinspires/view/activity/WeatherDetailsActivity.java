package br.com.josecarlos.weatherinspires.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import br.com.josecarlos.weatherinspires.R;
import br.com.josecarlos.weatherinspires.models.CityWeatherListVO;
import br.com.josecarlos.weatherinspires.models.WeatherPreferences_;
import br.com.josecarlos.weatherinspires.utils.Constants;
import br.com.josecarlos.weatherinspires.view.fragments.CityWeatherFragment;
import br.com.josecarlos.weatherinspires.view.fragments.CityWeatherFragment_;

/**
 * Created by jcjunior on 15/07/2016.
 */

@EActivity
public class WeatherDetailsActivity extends AppCompatActivity {

    private static final String TAG = WeatherDetailsActivity.class.getSimpleName();

    @Extra(Constants.CITY_WEATHER)
    CityWeatherListVO citySelected;

    @Extra(Constants.SEARCH_RESULT)
    String searchResult;

    @ViewById
    Toolbar weatherDetailToolbar;

    @Pref
    WeatherPreferences_ weatherPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather_detail);

        if (savedInstanceState == null) {

            CityWeatherFragment cityWeatherFragment = CityWeatherFragment_.builder()
                                .citySelected(citySelected)
                                .unit(weatherPreferences.unit().get())
                                .searchResult(searchResult)
                                .build();

            Log.d(TAG, "CityWeatherFragment attached objects with success. ");

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.containerDetails, cityWeatherFragment)
                    .commit();

            Log.d(TAG, "WeatherDetailsActivity called CityWeatherFragment with success");
        }

    }

    @AfterViews
    public void initComponents(){

        setSupportActionBar(weatherDetailToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }

}
