package com.weather.app.service.impl;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.app.dao.CityDao;
import com.weather.app.dao.WeatherDao;
import com.weather.app.entity.City;
import com.weather.app.entity.Weather;
import com.weather.app.model.WeatherModel;
import com.weather.app.service.WeatherService;
import com.weather.app.util.ApplicationUtils;
import com.weather.app.view.model.WeatherViewModel;

/**
 * The Class WeatherServiceImpl.
 */
@Service
@Transactional
public class WeatherServiceImpl implements WeatherService {
	
	/** The api key. */
	@Value("${openweather.apiKey}")
	private String apiKey;

	/** The rest template. */
	@Autowired
	private RestTemplate restTemplate;

	/** The city dao. */
	@Autowired
	private CityDao cityDao;

	/** The weather dao. */
	@Autowired
	private WeatherDao weatherDao;

	/** The object mapper. */
	@Autowired
	private ObjectMapper objectMapper;

	/* (non-Javadoc)
	 * @see com.weather.app.service.WeatherService#getWeatherFromAPI(java.lang.String)
	 */
	@Override
	@Cacheable("weather")
	public WeatherModel getWeatherFromAPI(String city) {
		URI url = new UriTemplate(ApplicationUtils.WEATHER_CITY_URL).expand(city, this.apiKey);
		return invoke(url, WeatherModel.class);
	}

	/* (non-Javadoc)
	 * @see com.weather.app.service.WeatherService#getWeatherList(java.lang.String, java.util.Date, int, int)
	 */
	@Override
	@Transactional
	public List<WeatherViewModel> getWeatherList(String city, Date date, int from, int pageSize)
			throws JsonParseException, JsonMappingException, IOException {
		List<WeatherViewModel> weatherViewModels = new ArrayList<>();
		List<Weather> weathers = weatherDao.getWeatherList(city, date, from, pageSize);
		if (weathers != null && !weathers.isEmpty()) {
			for (Weather weather : weathers) {
				weatherViewModels.add(objectMapper.readValue(weather.getWeatherData(), WeatherViewModel.class));
			}
		}
		return weatherViewModels;
	}

	/* (non-Javadoc)
	 * @see com.weather.app.service.WeatherService#getWeather(java.lang.String)
	 */
	@Override
	@Transactional
	public WeatherViewModel getWeather(String city) throws Exception {
		//get weather current date of city from db
		Weather weather = weatherDao.getWeatherByDateAndName(new Date(), city);
		WeatherViewModel weatherViewModel;
		if (weather == null) {
			//find city from open weather
			WeatherModel weatherModel = getWeatherFromAPI(city);
			weatherViewModel = new WeatherViewModel();
			BeanUtils.copyProperties(weatherModel, weatherViewModel);
			weatherViewModel.setfDate(weatherModel.getTimestamp());
			City c = cityDao.getCityByCityId(weatherModel.getId());
			//add city to db if not exist
			if (c == null || c.getCityId() == null) {
				c = addCity(weatherModel.getId(), weatherModel.getName());
			}
			//check weather exist or not, save weather if not
			weather = weatherDao.getWeatherByDate(weatherModel.getTimestamp(), c.getCityId());
			if (weather == null) {
				String data = objectMapper.writeValueAsString(weatherViewModel);
				weather = new Weather();
				weather.setWeatherCity(c);
				weather.setWeatherData(data);
				weather.setWeatherDate(weatherModel.getTimestamp());
				weatherDao.addWeather(weather);
			}
		} else {
			weatherViewModel = objectMapper.readValue(weather.getWeatherData(), WeatherViewModel.class);
		}
		return weatherViewModel;
	}

	/* (non-Javadoc)
	 * @see com.weather.app.service.WeatherService#addCity(java.lang.Integer, java.lang.String)
	 */
	@Override
	public City addCity(Integer cityId, String cityName) {
		City city = new City();
		city.setCityId(cityId);
		city.setCityName(cityName);
		return cityDao.addCity(city);
	}

	/* (non-Javadoc)
	 * @see com.weather.app.service.WeatherService#removeWeather(java.lang.Integer)
	 */
	@Override
	public void removeWeather(Integer cityId) {
		cityDao.removeCity(cityId);
	}

	/**
	 * Invoke.
	 *
	 * @param <T> the generic type
	 * @param url the url
	 * @param responseType the response type
	 * @return the t
	 */
	private <T> T invoke(URI url, Class<T> responseType) {
		RequestEntity<?> request = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();
		ResponseEntity<T> exchange = this.restTemplate.exchange(request, responseType);
		return exchange.getBody();
	}

}
