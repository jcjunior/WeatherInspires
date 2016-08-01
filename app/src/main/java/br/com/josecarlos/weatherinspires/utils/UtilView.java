package br.com.josecarlos.weatherinspires.utils;

import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by jcjunior on 15/07/2016.
 */
@EBean
public class UtilView {

    @RootContext
    Context context;

    public String getWeatherIconByName(String name){
        String weatherIconName = null;

        if (name != null && name.length() > 0) {
            String iconName = "wi_owm_" + name;
            weatherIconName = context.getString(getResIdByStringName(iconName));
        }

        return weatherIconName;
    }

    public String addDegree(String text){
        String numberWithDegree = null;
        String degreeUnicode = "\u00b0";

        if (text != null && text.length() > 0){
            numberWithDegree = text+degreeUnicode;
        }
        return numberWithDegree;
    }

    private int getResIdByStringName(String name){
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

}
