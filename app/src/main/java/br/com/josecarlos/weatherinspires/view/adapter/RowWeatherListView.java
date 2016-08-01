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

@EViewGroup(R.layout.row_weather_list_adapter)
public class RowWeatherListView extends LinearLayout {

    @ViewById
    TextView txtCityName;

    @ViewById
    WeatherIconView imgWeather;

    @ViewById
    TextView txtTemp;

    @Bean
    UtilView utilView;

    public RowWeatherListView(Context context) {
        super(context);
    }

    public void bind(CityWeatherListVO weatherVO) {

        if (weatherVO != null && weatherVO.getWeatherId() != null) {
            txtCityName.setText(weatherVO.getCityName());
            txtTemp.setText(utilView.addDegree(weatherVO.getTemp()));
            imgWeather.setIconResource(utilView.getWeatherIconByName(weatherVO.getWeatherId()));
            imgWeather.setIconSize(30);
        }
    }
}
