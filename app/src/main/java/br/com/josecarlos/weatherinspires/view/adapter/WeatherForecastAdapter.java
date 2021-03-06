package br.com.josecarlos.weatherinspires.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import br.com.josecarlos.weatherinspires.models.CityWeatherListVO;

/**
 * Created by jcjunior on 14/07/2016.
 */

@EBean
public class WeatherForecastAdapter extends BaseAdapter{

    private static final String TAG = WeatherForecastAdapter.class.getSimpleName();

    private List<CityWeatherListVO> weathers;

    @RootContext
    Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RowWeatherForecastView weatherRowForecastView;
        if (convertView == null) {
            weatherRowForecastView = RowWeatherForecastView_.build(context);
        } else {
            weatherRowForecastView = (RowWeatherForecastView) convertView;
        }

        weatherRowForecastView.bind(getItem(position));

        return weatherRowForecastView;
    }

    @Override
    public int getCount() {
        if (weathers != null) {
            return weathers.size();
        }
        return 0;
    }

    @Override
    public CityWeatherListVO getItem(int position) {
        if (weathers != null) {
            return weathers.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setWeathers(List<CityWeatherListVO> weathers) {
        this.weathers = weathers;
    }

    public List<CityWeatherListVO> getWeathers() {
        return weathers;
    }

    public void clear(){
        if (weathers != null && weathers.size() > 0){
            weathers = new ArrayList<CityWeatherListVO>();
        }
    }
}
