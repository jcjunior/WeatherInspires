package br.com.josecarlos.weatherinspires.view.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.pwittchen.weathericonview.WeatherIconView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.josecarlos.weatherinspires.R;
import br.com.josecarlos.weatherinspires.models.CityWeatherListVO;
import br.com.josecarlos.weatherinspires.utils.UtilView;

/**
 * Created by jcjunior on 15/07/2016.
 */

@EViewGroup(R.layout.row_weather_forecast_adapter)
public class RowWeatherForecastView extends LinearLayout {

    @ViewById
    TextView txtWeekDay;

    @ViewById
    WeatherIconView imgWeather;

    @ViewById
    TextView txtMaxTemp;

    @ViewById
    TextView txtMinTemp;

    @Bean
    UtilView utilView;

    public RowWeatherForecastView(Context context) {
        super(context);
    }

    public void bind(CityWeatherListVO weatherVO) {
        txtWeekDay.setText(weatherVO.getWeekDay());
        txtMaxTemp.setText(utilView.addDegree(weatherVO.getMaxTemp()));
        txtMinTemp.setText(utilView.addDegree(weatherVO.getMinTemp()));
        imgWeather.setIconResource(utilView.getWeatherIconByName(weatherVO.getWeatherId()));
        imgWeather.setIconSize(30);
    }
}
