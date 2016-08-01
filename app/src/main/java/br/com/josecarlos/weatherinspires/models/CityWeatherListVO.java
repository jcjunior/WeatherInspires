package br.com.josecarlos.weatherinspires.models;

import java.io.Serializable;

/**
 * Created by jcjunior on 14/07/2016.
 */

public class CityWeatherListVO implements Serializable {

    private String cityName;
    private String currentDate;
    private String weekDay;
    private String weatherId;
    private String maxTemp;
    private String minTemp;
    private String temp;
    private String weatherDescription;
    private boolean savedCity;
    private String errorMessage;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public boolean isSavedCity() {
        return savedCity;
    }

    public void setSavedCity(boolean savedCity) {
        this.savedCity = savedCity;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityWeatherListVO)) return false;

        CityWeatherListVO that = (CityWeatherListVO) o;

        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null)
            return false;
        if (currentDate != null ? !currentDate.equals(that.currentDate) : that.currentDate != null)
            return false;
        if (weekDay != null ? !weekDay.equals(that.weekDay) : that.weekDay != null) return false;
        if (weatherId != null ? !weatherId.equals(that.weatherId) : that.weatherId != null)
            return false;
        if (maxTemp != null ? !maxTemp.equals(that.maxTemp) : that.maxTemp != null) return false;
        if (minTemp != null ? !minTemp.equals(that.minTemp) : that.minTemp != null) return false;
        if (temp != null ? !temp.equals(that.temp) : that.temp != null) return false;
        return weatherDescription != null ? weatherDescription.equals(that.weatherDescription) : that.weatherDescription == null;

    }

    @Override
    public int hashCode() {
        int result = cityName != null ? cityName.hashCode() : 0;
        result = 31 * result + (currentDate != null ? currentDate.hashCode() : 0);
        result = 31 * result + (weekDay != null ? weekDay.hashCode() : 0);
        result = 31 * result + (weatherId != null ? weatherId.hashCode() : 0);
        result = 31 * result + (maxTemp != null ? maxTemp.hashCode() : 0);
        result = 31 * result + (minTemp != null ? minTemp.hashCode() : 0);
        result = 31 * result + (temp != null ? temp.hashCode() : 0);
        result = 31 * result + (weatherDescription != null ? weatherDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CityWeatherListVO{" +
                "cityName='" + cityName + '\'' +
                ", currentDate='" + currentDate + '\'' +
                ", weekDay='" + weekDay + '\'' +
                ", weatherId='" + weatherId + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                ", minTemp='" + minTemp + '\'' +
                ", temp='" + temp + '\'' +
                ", weatherDescription='" + weatherDescription + '\'' +
                '}';
    }

}
