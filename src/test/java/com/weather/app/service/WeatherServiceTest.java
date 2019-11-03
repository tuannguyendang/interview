package com.weather.app.service;

import static org.mockito.Mockito.spy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.app.dao.impl.CityDaoImpl;
import com.weather.app.dao.impl.WeatherDaoImpl;
import com.weather.app.entity.City;
import com.weather.app.entity.Weather;
import com.weather.app.model.WeatherModel;
import com.weather.app.service.impl.WeatherServiceImpl;
import com.weather.app.view.model.WeatherViewModel;

public class WeatherServiceTest {

	private String weatherData;

	@InjectMocks
	private WeatherServiceImpl weatherService;

	@Spy
	private ObjectMapper mapper;

	@Spy
	private RestTemplate restTemplate;

	@Spy
	private CityDaoImpl cityDao;

	@Spy
	private WeatherDaoImpl weatherDao;

	@Before
	public void setupTarget() {
		MockitoAnnotations.initMocks(this);
		try {
			Resource resource = new ClassPathResource("data.txt");
			InputStream resourceInputStream = resource.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(resourceInputStream));
			String str;
			StringBuilder builder = new StringBuilder();
			while ((str = in.readLine()) != null)
				builder.append(str);
			in.close();
			weatherData = builder.toString();
		} catch (IOException e) {
		}
	}

	@Test
	public void testGetWeatherWhenDataExitsWillReturnWeatherDB() throws Exception {
		String city = "Thanh pho Ho Chi Minh";
		WeatherModel weatherModel = new WeatherModel();
		Weather weather = new Weather();
		weather.setWeatherData(weatherData);
		WeatherServiceImpl wsv = spy(weatherService);
		Mockito.doReturn(weatherModel).when(wsv).getWeatherFromAPI(Mockito.anyString());
		Mockito.doReturn(weather).when(weatherDao).getWeatherByDateAndName(Mockito.anyObject(), Mockito.anyString());
		WeatherViewModel weatherViewModel = weatherService.getWeather(city);
		Assert.assertNotNull(weatherViewModel);
		Assert.assertEquals(new Integer(1566083), weatherViewModel.getId());
		Assert.assertEquals(city, weatherViewModel.getName());
	}

	@Test
	public void testGetWeatherWhenDataNotExitsWillReturnWeatherFromAPI() throws Exception {
		String city = "Thanh pho Ho Chi Minh";
		WeatherModel weatherModel = new WeatherModel();
		weatherModel.setTimestamp(1000000);
		weatherModel.setId(1566083);
		weatherModel.setName(city);
		Weather weather = null;
		City c = new City();
		c.setCityId(1566083);

		WeatherServiceImpl wsv = spy(weatherService);
		Mockito.doReturn(weatherModel).when(wsv).getWeatherFromAPI(Mockito.anyString());
		Mockito.doReturn(weather).when(weatherDao).getWeatherByDateAndName(Mockito.anyObject(), Mockito.anyString());
		Mockito.doReturn(c).when(cityDao).getCityByCityId(Mockito.anyObject());
		Mockito.doReturn(weather).when(weatherDao).getWeatherByDate(Mockito.anyObject(), Mockito.anyObject());
		Mockito.doNothing().when(weatherDao).addWeather(Mockito.anyObject());

		WeatherViewModel weatherViewModel = wsv.getWeather(city);
		Assert.assertNotNull(weatherViewModel);
		Assert.assertEquals(new Integer(1566083), weatherViewModel.getId());
		Assert.assertEquals(city, weatherViewModel.getName());
	}

	@Test
	public void testGetWeatherWhenDataExitsWillReturnWeatherFromAPI() throws Exception {
		String city = "Thanh pho Ho Chi Minh";
		WeatherModel weatherModel = new WeatherModel();
		weatherModel.setTimestamp(1000000);
		weatherModel.setId(1566083);
		weatherModel.setName(city);
		Weather weather = null;
		Weather weatherBD = new Weather();
		City c = new City();
		c.setCityId(1566083);

		WeatherServiceImpl wsv = spy(weatherService);
		Mockito.doReturn(weatherModel).when(wsv).getWeatherFromAPI(Mockito.anyString());
		Mockito.doReturn(weather).when(weatherDao).getWeatherByDateAndName(Mockito.anyObject(), Mockito.anyString());
		Mockito.doReturn(c).when(cityDao).getCityByCityId(Mockito.anyObject());
		Mockito.doReturn(weatherBD).when(weatherDao).getWeatherByDate(Mockito.anyObject(), Mockito.anyObject());
		Mockito.doNothing().when(weatherDao).addWeather(Mockito.anyObject());

		WeatherViewModel weatherViewModel = wsv.getWeather(city);
		Assert.assertNotNull(weatherViewModel);
		Assert.assertEquals(new Integer(1566083), weatherViewModel.getId());
		Assert.assertEquals(city, weatherViewModel.getName());
	}

	@Test
	public void testRemoveCity() {
		WeatherServiceImpl wsv = spy(weatherService);
		Mockito.doNothing().when(cityDao).removeCity(Mockito.anyInt());
		wsv.removeWeather(Mockito.anyInt());
	}

	@Test
	public void testgetWeatherListWhenDataExistWillReturnListData()
			throws JsonParseException, JsonMappingException, IOException {
		List<WeatherViewModel> weatherViewModels = new ArrayList<>();
		List<Weather> weathers = new ArrayList<>();
		Weather weather = new Weather();
		weather.setWeatherData(weatherData);
		weathers.add(weather);

		WeatherServiceImpl wsv = spy(weatherService);
		Mockito.doReturn(weatherViewModels).when(wsv).getWeatherList(Mockito.anyString(), Mockito.anyObject(),
				Mockito.anyInt(), Mockito.anyInt());
		Mockito.doReturn(weathers).when(weatherDao).getWeatherList(Mockito.anyString(), Mockito.anyObject(),
				Mockito.anyInt(), Mockito.anyInt());
		List<WeatherViewModel> ws = weatherService.getWeatherList(Mockito.anyString(), Mockito.anyObject(),
				Mockito.anyInt(), Mockito.anyInt());
		Assert.assertNotNull(ws);
		Assert.assertEquals(1, ws.size());
		Assert.assertNotNull(ws.get(0));
		Assert.assertEquals(new Integer(1566083), ws.get(0).getId());
		Assert.assertEquals("Thanh pho Ho Chi Minh", ws.get(0).getName());
	}

	@Test
	public void testgetWeatherListWhenDataNotExistWillReturnEmptyList()
			throws JsonParseException, JsonMappingException, IOException {
		List<Weather> weathers = new ArrayList<>();

		Mockito.doReturn(weathers).when(weatherDao).getWeatherList(Mockito.anyString(), Mockito.anyObject(),
				Mockito.anyInt(), Mockito.anyInt());
		List<WeatherViewModel> ws = weatherService.getWeatherList(Mockito.anyString(), Mockito.anyObject(),
				Mockito.anyInt(), Mockito.anyInt());
		Assert.assertNotNull(ws);
		Assert.assertEquals(0, ws.size());
	}

	@Test(expected = IOException.class)
	public void testgetWeatherListWhenExceptionWillThrow()
			throws JsonParseException, JsonMappingException, IOException {
		Mockito.doThrow(IOException.class).when(weatherDao).getWeatherList(Mockito.anyString(), Mockito.anyObject(),
				Mockito.anyInt(), Mockito.anyInt());
		weatherService.getWeatherList(Mockito.anyString(), Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt());
	}
}
