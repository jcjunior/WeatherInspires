package br.com.josecarlos.weatherinspires.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jcjunior on 15/07/2016.
 */
public class Weather {

    @SerializedName("id")
    private Integer id;

    @SerializedName("description")
    private String description;

    @SerializedName("main")
    private String weather;

    public Weather(Integer id, String description, String weather) {
        this.id = id;
        this.description = description;
        this.weather = weather;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", weather='" + weather + '\'' +
                '}';
    }
}
