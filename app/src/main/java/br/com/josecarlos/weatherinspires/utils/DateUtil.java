package br.com.josecarlos.weatherinspires.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jcjunior on 15/07/2016.
 */
public class DateUtil {

    private static final String TAG = DateUtil.class.getSimpleName();

    public static String formatDateLongToString(Long date) {
        String dateString = null;

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH);

        try {
            dateString = dateFormat.format(date);
        } catch (Exception e) {
            Log.e(TAG, "Error on formating this ["+date+"] with this pattern ["+DateFormat.getDateInstance().toString()+"]. " + e.getMessage());
            return null;
        }

        return dateString;
    }

    public static String getWeekDayOfADate(String dateStr, String pattern){

        String day = null;

        if (dateStr != null && pattern != null) {

            Date date = parseDateStringToDate(dateStr, pattern);

            if (date != null) {
                day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
            }

        }

        return day;
    }

    public static Integer getDayOfDate(String dateStr, String pattern){

        Integer day = null;

        Calendar cal = Calendar.getInstance();
        Date date = parseDateStringToDate(dateStr, pattern);

        if (date != null){
            cal.setTime(date);
            day = cal.get(Calendar.DAY_OF_MONTH);
        }

        return day;
    }

    public static Date parseDateStringToDate(String dateStr, String pattern) {

        Date date = null;

        if (dateStr != null && pattern != null) {

            SimpleDateFormat format = new SimpleDateFormat(pattern);

            try {
                date = format.parse(dateStr);
            } catch (ParseException e) {
                Log.e(TAG, "Error on parsing this [" + dateStr + "] with this pattern [" + pattern + "]. " + e.getMessage());
                return null;
            }
        }

        return date;
    }


}
