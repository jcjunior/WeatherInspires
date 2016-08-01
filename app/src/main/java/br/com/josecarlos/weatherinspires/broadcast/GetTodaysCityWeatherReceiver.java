package br.com.josecarlos.weatherinspires.broadcast;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import br.com.josecarlos.weatherinspires.delegate.GetTodaysCityWeatherDelegate;
import br.com.josecarlos.weatherinspires.models.CityWeatherListVO;

/**
 * Created by jcjunior on 16/07/2016.
 */
public class GetTodaysCityWeatherReceiver extends BroadcastReceiver {

    private static final String TAG = GetTodaysCityWeatherReceiver.class.getSimpleName();

    private GetTodaysCityWeatherDelegate delegate;

    private static final String CITY_WEATHER_OBSERVER = "CITY WEATHER OBSERVER";
    private static final String CITY_WEATHER_RESPONSE = "CITY WEATHER RESPONSE";
    private static final String EXCEPTION = "EXCEPTION";

    @Override
    public void onReceive(Context context, Intent intent) {

        Exception exception = (Exception) intent.getSerializableExtra(EXCEPTION);

        if (exception == null){
            delegate.manageGetTodaysCityWeatherSuccess((CityWeatherListVO) intent.getSerializableExtra(CITY_WEATHER_RESPONSE));
        } else {
            delegate.managerCallbackError(exception);
        }
    }

    /**
     * Registers a new broadcast receiver.
     *
     * @param delegate
     * @return
     */
    public static GetTodaysCityWeatherReceiver registerObserver(GetTodaysCityWeatherDelegate delegate){

        GetTodaysCityWeatherReceiver receiver = null;

        try {

            receiver = new GetTodaysCityWeatherReceiver();
            receiver.delegate = delegate;
            LocalBroadcastManager.getInstance(delegate.retrieveApplication()).registerReceiver(receiver, new IntentFilter(CITY_WEATHER_OBSERVER));

        } catch(Throwable ex) {
            Log.e(TAG, "Error on register observer [" + CITY_WEATHER_OBSERVER + "]", ex);
        }

        return receiver;
    }

    /**
     * Unregister a broadcast receiver that was previously registered.
     *
     * @param application
     */
    public void unregisterObserver(Application application){

        try {

            LocalBroadcastManager.getInstance(application).unregisterReceiver(this);

        } catch(Throwable ex) {
            Log.e(TAG, "Error on unregister observer [" + CITY_WEATHER_OBSERVER + "]", ex);
        }

    }

    /**
     * Notify a registered observer that exist new data
     *
     * @param context
     * @param result
     */
    public static void notifyNewParametersReceived(Context context, CityWeatherListVO result){

        try {

            Intent intent = new Intent(CITY_WEATHER_OBSERVER);
            intent.putExtra(CITY_WEATHER_RESPONSE, result);

            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        } catch(Throwable ex) {
            Log.e(TAG, "Error on notify new data to observer [" + CITY_WEATHER_OBSERVER + "]", ex);
        }
    }

    /**
     * Notify a registered observer that occurred an error
     *
     * @param context
     * @param e
     */
    public static void notifyErrorReceived(Context context, Exception e){

        try {

            Intent intent = new Intent(CITY_WEATHER_OBSERVER);
            intent.putExtra(EXCEPTION, e);

            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        } catch(Throwable ex) {
            Log.e(TAG, "Error on notify an error to observer [" + CITY_WEATHER_OBSERVER + "]", ex);
        }
    }
}
