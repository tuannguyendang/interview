package com.weather.app.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class City.
 */
@Entity
@Table(name = "city")
public class City {
	
	/** The city id. */
	@Id
	@Column(name = "id")
	private Integer cityId;

	/** The city name. */
	@Column(name = "name")
	private String cityName;
	
	/** The Weather forecasts. */
	@OneToMany(fetch= FetchType.LAZY, mappedBy = "weatherCity", cascade= CascadeType.REMOVE)
	List<Weather> WeatherForecasts;

	/**
	 * Gets the city id.
	 *
	 * @return the city id
	 */
	public Integer getCityId() {
		return cityId;
	}

	/**
	 * Sets the city id.
	 *
	 * @param cityId the new city id
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	/**
	 * Gets the city name.
	 *
	 * @return the city name
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * Sets the city name.
	 *
	 * @param cityName the new city name
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
