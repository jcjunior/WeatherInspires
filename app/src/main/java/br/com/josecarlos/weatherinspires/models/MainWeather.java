package br.com.josecarlos.weatherinspires.models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by jcjunior on 15/07/2016.
 */
public class MainWeather {

    @SerializedName("temp")
    private Double temp;

    @SerializedName("pressure")
    private Double pressure;

    @SerializedName("humidity")
    private Integer humidity;

    @SerializedName("temp_min")
    private Double tempMin;

    @SerializedName("temp_max")
    private Double tempMax;

    public Integer getTemp() {
        if(temp != null) {
            return new BigDecimal(temp.toString()).setScale(0, RoundingMode.HALF_UP).intValue();
        }
        return null;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getTempMin() {
        if (tempMin != null){
            return new BigDecimal(tempMin.toString()).setScale(0, RoundingMode.HALF_UP).intValue();
        }
        return null;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Integer getTempMax() {
        if (tempMax != null){
            return new BigDecimal(tempMax.toString()).setScale(0, RoundingMode.HALF_UP).intValue();
        }
        return null;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    @Override
    public String toString() {
        return "MainWeather{" +
                "temp=" + temp +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                '}';
    }
}
