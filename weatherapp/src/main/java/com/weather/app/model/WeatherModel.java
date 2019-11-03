package com.weather.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.weather.app.util.ApplicationUtils;

/**
 * The Class WeatherModel.
 */
public class WeatherModel implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The name. */
	private String name;
	
	/** The id. */
	private Integer id;
	
	/** The timestamp. */
	private Date timestamp;
	
	/** The temperature. */
	private String temperature;
	
	/** The pressure. */
	private String pressure;
	
	/** The humidity. */
	private String humidity;
	
	/** The wind speed. */
	private String windSpeed;
	
	/** The clouds all. */
	private String cloudsAll;
	
	/** The weather main. */
	private String weatherMain;
	
	/** The weather id. */
	private Integer weatherId;
	
	/** The weather icon. */
	private String weatherIcon;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	@JsonProperty("timestamp")
	public Date getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param unixTime the new timestamp
	 */
	@JsonSetter("dt")
	public void setTimestamp(long unixTime) {
		this.timestamp = new Date(unixTime * 1000);
	}

	/**
	 * Sets the wind speed.
	 *
	 * @param winds the winds
	 */
	@JsonSetter("wind")
	public void setWindSpeed(Map<String, Object> winds) {
		setWindSpeed(String.valueOf(winds.get("speed")));
	}

	/**
	 * Sets the clouds all.
	 *
	 * @param clouds the clouds
	 */
	@JsonProperty("clouds")
	public void setCloudsAll(Map<String, Object> clouds) {
		setCloudsAll(String.valueOf(clouds.get("all")));
	}

	/**
	 * Sets the main.
	 *
	 * @param main the main
	 */
	@JsonProperty("main")
	public void setMain(Map<String, Object> main) {
		setTemperature(ApplicationUtils.changeTemperatureKC(Double.parseDouble(main.get("temp").toString())));
		setPressure(String.valueOf(main.get("pressure")));
		setHumidity(String.valueOf(main.get("humidity")));
	}

	/**
	 * Sets the weather.
	 *
	 * @param weatherEntries the weather entries
	 */
	@JsonProperty("weather")
	public void setWeather(List<Map<String, Object>> weatherEntries) {
		Map<String, Object> weather = weatherEntries.get(0);
		setWeatherId((Integer) weather.get("id"));
		setWeatherIcon(String.valueOf(weather.get("icon")));
		setWeatherMain(String.valueOf(weather.get("main")));
	}

	/**
	 * Gets the weather icon.
	 *
	 * @return the weather icon
	 */
	public String getWeatherIcon() {
		return "http://openweathermap.org/img/w/" + this.weatherIcon + ".png";
	}

	/**
	 * Gets the temperature.
	 *
	 * @return the temperature
	 */
	public String getTemperature() {
		return temperature;
	}

	/**
	 * Sets the temperature.
	 *
	 * @param temperature the new temperature
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	/**
	 * Gets the pressure.
	 *
	 * @return the pressure
	 */
	public String getPressure() {
		return pressure;
	}

	/**
	 * Sets the pressure.
	 *
	 * @param pressure the new pressure
	 */
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	/**
	 * Gets the humidity.
	 *
	 * @return the humidity
	 */
	public String getHumidity() {
		return humidity;
	}

	/**
	 * Sets the humidity.
	 *
	 * @param humidity the new humidity
	 */
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	/**
	 * Gets the wind speed.
	 *
	 * @return the wind speed
	 */
	public String getWindSpeed() {
		return windSpeed;
	}

	/**
	 * Sets the wind speed.
	 *
	 * @param windSpeed the new wind speed
	 */
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	/**
	 * Gets the clouds all.
	 *
	 * @return the clouds all
	 */
	public String getCloudsAll() {
		return cloudsAll;
	}

	/**
	 * Sets the clouds all.
	 *
	 * @param cloudsAll the new clouds all
	 */
	public void setCloudsAll(String cloudsAll) {
		this.cloudsAll = cloudsAll;
	}

	/**
	 * Gets the weather main.
	 *
	 * @return the weather main
	 */
	public String getWeatherMain() {
		return weatherMain;
	}

	/**
	 * Sets the weather main.
	 *
	 * @param weatherMain the new weather main
	 */
	public void setWeatherMain(String weatherMain) {
		this.weatherMain = weatherMain;
	}

	/**
	 * Gets the weather id.
	 *
	 * @return the weather id
	 */
	public Integer getWeatherId() {
		return this.weatherId;
	}

	/**
	 * Sets the weather id.
	 *
	 * @param weatherId the new weather id
	 */
	public void setWeatherId(Integer weatherId) {
		this.weatherId = weatherId;
	}

	/**
	 * Sets the weather icon.
	 *
	 * @param weatherIcon the new weather icon
	 */
	public void setWeatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}
}
