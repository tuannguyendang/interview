package com.weather.app.dao;

import com.weather.app.entity.City;

/**
 * The Interface CityDao.
 */
public interface CityDao {
	
	/**
	 * Adds the city.
	 *
	 * @param city the city
	 * @return the city
	 */
	public City addCity(City city);

	/**
	 * Gets the city by city id.
	 *
	 * @param cityId the city id
	 * @return the city by city id
	 */
	public City getCityByCityId(Integer cityId);
	
	/**
	 * Removes the city.
	 *
	 * @param cityId the city id
	 */
	public void removeCity(Integer cityId);
	
	/**
	 * Gets the city by city name.
	 *
	 * @param cityName the city name
	 * @return the city by city name
	 */
	public City getCityByCityName(String cityName);
}
