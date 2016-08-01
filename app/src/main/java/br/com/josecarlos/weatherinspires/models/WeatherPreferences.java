package br.com.josecarlos.weatherinspires.models;

import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.DefaultStringSet;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import java.util.Set;

/**
 * Created by jcjunior on 15/07/2016.
 */
@SharedPref(SharedPref.Scope.UNIQUE)
public interface WeatherPreferences {

    @DefaultStringSet( value = "")
    Set<String> cities();

    /**
     * weather units
     *
     * Celsius = "metric"
     * Fahrenheit = "imperial"
     * Kelvin = ""
     * @return
     */
    @DefaultString("metric")
    String unit();
}
