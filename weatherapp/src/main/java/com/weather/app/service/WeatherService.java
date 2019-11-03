package com.weather.app.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.weather.app.entity.City;
import com.weather.app.model.WeatherModel;
import com.weather.app.view.model.WeatherViewModel;

/**
 * The Interface WeatherService.
 */
public interface WeatherService {
	
	/**
	 * Gets the weather list.
	 *
	 * @param city the city
	 * @param date the date
	 * @param from the from
	 * @param pageSize the page size
	 * @return the weather list
	 * @throws JsonParseException the json parse exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	List<WeatherViewModel> getWeatherList(String city, Date date, int from, int pageSize)
			throws JsonParseException, JsonMappingException, IOException;

	/**
	 * Gets the weather.
	 *
	 * @param city the city
	 * @return the weather
	 * @throws Exception the exception
	 */
	WeatherViewModel getWeather(String city) throws Exception;

	/**
	 * Removes the weather.
	 *
	 * @param cityId the city id
	 */
	public void removeWeather(Integer cityId);

	/**
	 * Adds the city.
	 *
	 * @param cityId the city id
	 * @param cityName the city name
	 * @return the city
	 */
	public City addCity(Integer cityId, String cityName);

	/**
	 * Gets the weather from API.
	 *
	 * @param city the city
	 * @return the weather from API
	 */
	public WeatherModel getWeatherFromAPI(String city);
}
