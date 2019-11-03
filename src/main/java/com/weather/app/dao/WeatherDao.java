package com.weather.app.dao;

import java.util.Date;
import java.util.List;

import com.weather.app.entity.Weather;

/**
 * The Interface WeatherDao.
 */
public interface WeatherDao {
	
	/**
	 * Adds the weather.
	 *
	 * @param weather the weather
	 */
	public void addWeather(Weather weather);
	
	/**
	 * Gets the weather by date.
	 *
	 * @param date the date
	 * @param cityId the city id
	 * @return the weather by date
	 */
	public Weather getWeatherByDate(Date date, Integer cityId);
	
	/**
	 * Gets the weather list.
	 *
	 * @param city the city
	 * @param date the date
	 * @param from the from
	 * @param pageSize the page size
	 * @return the weather list
	 */
	public List<Weather> getWeatherList(String city, Date date, int from, int pageSize);
	
	/**
	 * Gets the weather by date and name.
	 *
	 * @param date the date
	 * @param cityName the city name
	 * @return the weather by date and name
	 */
	public Weather getWeatherByDateAndName(Date date, String cityName);
}
