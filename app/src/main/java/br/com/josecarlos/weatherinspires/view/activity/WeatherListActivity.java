package br.com.josecarlos.weatherinspires.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import br.com.josecarlos.weatherinspires.R;
import br.com.josecarlos.weatherinspires.models.WeatherPreferences_;
import br.com.josecarlos.weatherinspires.utils.Constants;
import br.com.josecarlos.weatherinspires.view.fragments.CityWeatherListFragment;
import br.com.josecarlos.weatherinspires.view.fragments.CityWeatherListFragment_;

@EActivity
@OptionsMenu(R.menu.weather_list)
public class WeatherListActivity extends AppCompatActivity {

    private static final String TAG = WeatherListActivity.class.getSimpleName();

    @Pref
    WeatherPreferences_ weatherPreferences;

    @ViewById
    Toolbar weatherListToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather);

        if (savedInstanceState == null) {

            callWeatherListFragment();
        }
    }

    private void callWeatherListFragment() {
        SharedPreferences app_preferences = getSharedPreferences(Constants.APP_SHARED_PREFERENCES, MODE_PRIVATE);

        Set<String> savedCities = app_preferences.getStringSet(Constants.CITY_NAMES, new TreeSet<String>());

        CityWeatherListFragment cityWeatherListFragment = CityWeatherListFragment_.builder()
                .citiesNames(savedCities.toArray(new String[savedCities.size()]))
                .unit(weatherPreferences.unit().get())
                .build();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, cityWeatherListFragment)
                .commit();

        Log.d(TAG, "WeatherListActivity called CityWeatherListFragment with success");
    }

    @AfterViews
    public void initPreferences(){

        setSupportActionBar(weatherListToolbar);

        Set<String> defaultCities = new TreeSet<>();
        defaultCities.addAll(Arrays.asList("Dublin" ,"Barcelona", "London", "New York"));
        SharedPreferences app_preferences = getSharedPreferences(Constants.APP_SHARED_PREFERENCES, MODE_PRIVATE);

        if (!app_preferences.contains(Constants.CITY_NAMES)){

            //persist default values
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putStringSet(Constants.CITY_NAMES, new TreeSet<>(defaultCities));
            editor.commit();

            //initialize default values on temporary shared preferences
            weatherPreferences.edit().cities().put(defaultCities).apply();
        } else {

            //initialize values on temporary shared preferences
            Set<String> savedCities = app_preferences.getStringSet(Constants.CITY_NAMES, new TreeSet<String>());
            weatherPreferences.edit().cities().put(savedCities).apply();

        }

    }

    @OptionsItem(R.id.action_search)
    void searchCity(MenuItem item) {

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent = WeatherDetailsActivity_.intent(WeatherListActivity.this).get();
                intent.putExtra(Constants.SEARCH_RESULT, query);
                startActivity(intent);

                Log.d(TAG, "WeatherListActivity called CityWeatherFragment with success");

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @OptionsItem(R.id.action_refresh)
    void refresh() {
        callWeatherListFragment();
    }

}
