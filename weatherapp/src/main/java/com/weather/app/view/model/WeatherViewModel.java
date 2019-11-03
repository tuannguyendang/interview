package com.weather.app.view.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weather.app.util.ApplicationUtils;

/**
 * The Class WeatherViewModel.
 */
public class WeatherViewModel {
	
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
	
	/** The date. */
	private String fDate;
	
	/** The name. */
	private String name;
	
	/** The id. */
	private Integer id;
	
	/** The from index. */
	private Integer fromIndex = 0;
	
	/** The message. */
	@JsonIgnore
	private String message;

	/**
	 * Instantiates a new weather view model.
	 */
	public WeatherViewModel() {
		super();
	}

	/**
	 * Instantiates a new weather view model.
	 *
	 * @param message the message
	 */
	public WeatherViewModel(String message) {
		super();
		this.message = message;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the f date.
	 *
	 * @return the f date
	 */
	public String getfDate() {
		return this.fDate;
	}

	/**
	 * Sets the f date.
	 *
	 * @param fDate the new f date
	 */
	public void setfDate(String fDate) {
		this.fDate = fDate;
	}

	/**
	 * Sets the f date.
	 *
	 * @param date the new f date
	 */
	public void setfDate(Date date) {
		this.fDate = ApplicationUtils.dateFormat(date);
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
		return weatherId;
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
	 * Gets the weather icon.
	 *
	 * @return the weather icon
	 */
	public String getWeatherIcon() {
		return weatherIcon;
	}

	/**
	 * Sets the weather icon.
	 *
	 * @param weatherIcon the new weather icon
	 */
	public void setWeatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * Gets the from index.
	 *
	 * @return the from index
	 */
	public Integer getFromIndex() {
		return fromIndex;
	}

	/**
	 * Sets the from index.
	 *
	 * @param fromIndex the new from index
	 */
	public void setFromIndex(Integer fromIndex) {
		this.fromIndex = fromIndex;
	}
}
