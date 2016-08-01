package br.com.josecarlos.weatherinspires.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jcjunior on 15/07/2016.
 */
public class CityWeather {

    @SerializedName("cod")
    private Integer cod;

    @SerializedName("message")
    private String errorMessage;

    @SerializedName("weather")
    private List<Weather> weathers;

    @SerializedName("main")
    private MainWeather main;

    @SerializedName("dt")
    private Long date;

    @SerializedName("name")
    private String cityName;

    @SerializedName("dt_txt")
    private String dateText;

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public MainWeather getMain() {
        return main;
    }

    public void setMain(MainWeather main) {
        this.main = main;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public Long getDate() {
        if (date > 0){
            return date * 1000; // miliseconds
        }
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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
        if (!(o instanceof CityWeather)) return false;

        CityWeather that = (CityWeather) o;

        if (cod != null ? !cod.equals(that.cod) : that.cod != null) return false;
        if (errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null)
            return false;
        if (weathers != null ? !weathers.equals(that.weathers) : that.weathers != null)
            return false;
        if (main != null ? !main.equals(that.main) : that.main != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null)
            return false;
        return dateText != null ? dateText.equals(that.dateText) : that.dateText == null;

    }

    @Override
    public int hashCode() {
        int result = cod != null ? cod.hashCode() : 0;
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + (weathers != null ? weathers.hashCode() : 0);
        result = 31 * result + (main != null ? main.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        result = 31 * result + (dateText != null ? dateText.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CityWeather{" +
                "cod=" + cod +
                ", errorMessage='" + errorMessage + '\'' +
                ", weathers=" + weathers +
                ", main=" + main +
                ", date=" + date +
                ", cityName='" + cityName + '\'' +
                ", dateText='" + dateText + '\'' +
                '}';
    }
}
