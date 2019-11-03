package com.weather.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ModelMap;

import com.weather.app.service.impl.WeatherServiceImpl;
import com.weather.app.view.model.WeatherViewModel;

import junit.framework.Assert;

public class WeatherControllerTest {

	@SuppressWarnings("static-access")
	@Before
	public void setupTarget() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils r = new ReflectionTestUtils();
		r.setField(weatherController, "pageSize", 10);
	}

	@InjectMocks
	WeatherController weatherController;

	@Spy
	WeatherServiceImpl weatherService;

	@Test
	public void testSerachCityWearherWhenInputValidWillReturnData() throws Exception {
		ModelMap model = new ModelMap();
		String city = "Thanh pho Ho Chi Minh";
		WeatherViewModel weatherModels = new WeatherViewModel();
		weatherModels.setName(city);
		Mockito.doReturn(weatherModels).when(weatherService).getWeather(Mockito.anyString());
		String page = weatherController.serachCityWearher(model, city);
		Assert.assertEquals("weather", page);
		Assert.assertNotNull(model.get("weather"));
		weatherModels = (WeatherViewModel) model.get("weather");
		Assert.assertEquals(city, weatherModels.getName());
	}

	@Test
	public void testSerachCityWearherWhenInputNullCityWillReturnMessage() throws Exception {
		ModelMap model = new ModelMap();
		String city = null;
		WeatherViewModel weatherModels = new WeatherViewModel();
		weatherModels.setName(city);
		Mockito.doReturn(weatherModels).when(weatherService).getWeather(Mockito.anyString());
		String page = weatherController.serachCityWearher(model, city);
		Assert.assertEquals("weather", page);
		Assert.assertNotNull(model.get("weather"));
		weatherModels = (WeatherViewModel) model.get("weather");
		Assert.assertEquals("City Name is required!", weatherModels.getMessage());
	}

	@Test
	public void testSerachCityWearherWhenThrowExceptionWillReturnMessage() throws Exception {
		ModelMap model = new ModelMap();
		String city = "Thanh pho Ho Chi Minh";
		WeatherViewModel weatherModels = new WeatherViewModel();
		weatherModels.setName(city);
		Mockito.doThrow(Exception.class).when(weatherService).getWeather(Mockito.anyString());
		String page = weatherController.serachCityWearher(model, city);
		Assert.assertEquals("weather", page);
		Assert.assertNotNull(model.get("weather"));
		weatherModels = (WeatherViewModel) model.get("weather");
		Assert.assertEquals("City Name not found!", weatherModels.getMessage());
	}

	@Test
	public void testGetWeatherListWhenInputValidWillReturnPage() throws Exception {
		List<WeatherViewModel> weatherViewModels = new ArrayList<>();
		ModelMap model = new ModelMap();
		String city = "Thanh pho Ho Chi Minh";
		WeatherViewModel weatherModels = new WeatherViewModel();
		weatherModels.setfDate("Jun 1,2017");
		weatherModels.setName(city);
		weatherModels.setFromIndex(0);
		Mockito.doReturn(weatherViewModels).when(weatherService).getWeatherList(Mockito.anyString(),
				Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt());
		String page = weatherController.showMoreWeather(model, weatherModels);
		Assert.assertEquals("weatherlist", page);
		Assert.assertNotNull(model.get("weather"));
		Assert.assertNotNull(model.get("weatherModels"));
		weatherModels = (WeatherViewModel) model.get("weather");
		Assert.assertEquals(city, weatherModels.getName());
		Assert.assertEquals(new Integer(10), weatherModels.getFromIndex());
	}

	@Test
	public void testGetWeatherListWhenInputNullWillReturnMessage() throws Exception {
		List<WeatherViewModel> weatherViewModels = new ArrayList<>();
		ModelMap model = new ModelMap();
		String city = null;
		WeatherViewModel weatherModels = new WeatherViewModel();
		weatherModels.setfDate("Jun 1,2017");
		weatherModels.setName(city);
		weatherModels.setFromIndex(0);
		Mockito.doReturn(weatherViewModels).when(weatherService).getWeatherList(Mockito.anyString(),
				Mockito.anyObject(), Mockito.anyInt(), Mockito.anyInt());
		String page = weatherController.showMoreWeather(model, weatherModels);
		Assert.assertEquals("weatherlist", page);
		Assert.assertNotNull(model.get("weather"));
		weatherModels = (WeatherViewModel) model.get("weather");
		Assert.assertEquals("City Name is required!", weatherModels.getMessage());
	}

	@Test
	public void testGetWeatherListWhenThrowExceptionWillReturnMessage() throws Exception {
		ModelMap model = new ModelMap();
		String city = "Thanh pho Ho Chi Minh";
		WeatherViewModel weatherModels = new WeatherViewModel();
		weatherModels.setfDate("Jun 1,2017");
		weatherModels.setName(city);
		weatherModels.setFromIndex(0);
		Mockito.doThrow(IOException.class).when(weatherService).getWeatherList(Mockito.anyString(), Mockito.anyObject(),
				Mockito.anyInt(), Mockito.anyInt());
		String page = weatherController.showMoreWeather(model, weatherModels);
		Assert.assertEquals("weatherlist", page);
		Assert.assertNotNull(model.get("weather"));
		weatherModels = (WeatherViewModel) model.get("weather");
		Assert.assertEquals("Can not get history weather of city!", weatherModels.getMessage());
	}
	
	@Test
	public void testRemoveWeatherCity() throws Exception {
		ModelMap model = new ModelMap();
		int city = 1223;
		Mockito.doNothing().when(weatherService).removeWeather(Mockito.anyInt());
		String page = weatherController.deleteCity(model, city);
		Assert.assertEquals("weather", page);
		Assert.assertNotNull(model.get("weather"));
	}
}
