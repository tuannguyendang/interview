package com.weather.app.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.weather.app.service.WeatherService;
import com.weather.app.util.ApplicationUtils;
import com.weather.app.view.model.WeatherViewModel;

@Controller
@RequestMapping("/")
public class WeatherController {

	private Logger log = Logger.getLogger(WeatherController.class);

	@Value("${openweather.pageSize}")
	private Integer pageSize;

	@Autowired
	private WeatherService weatherService;

	/**
	 * Serach city wearher.
	 *
	 * @param model the model
	 * @param city the city
	 * @return the string
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String serachCityWearher(ModelMap model, @RequestParam(value = "name", required = false) String city) {
		if (city == null || city.isEmpty()) {
			model.addAttribute("weather", new WeatherViewModel("City Name is required!"));
		} else {
			try {
				WeatherViewModel weatherModels = weatherService.getWeather(city);
				model.addAttribute("weather", weatherModels);
			} catch (Exception e) {
				model.addAttribute("weather", new WeatherViewModel("City Name not found!"));
				log.error(e.getMessage());
			}
		}
		return "weather";
	}

	/**
	 * Show more weather.
	 *
	 * @param model the model
	 * @param weather the weather
	 * @return the string
	 */
	@RequestMapping(value = "/showmore", method = RequestMethod.POST)
	public String showMoreWeather(ModelMap model, @ModelAttribute("weather") WeatherViewModel weather) {
		try {
			if (weather.getName() == null || weather.getName().isEmpty()) {
				weather.setMessage("City Name is required!");
			} else {
				List<WeatherViewModel> weatherViewModels = weatherService.getWeatherList(weather.getName(),
						ApplicationUtils.convertStringToDate(weather.getfDate()), weather.getFromIndex(), pageSize);
				model.addAttribute("weatherModels", weatherViewModels);
				weather.setFromIndex(weather.getFromIndex() + pageSize);
			}
		} catch (IOException | ParseException e) {
			weather.setMessage("Can not get history weather of city!");
			log.error(e.getMessage());
		} finally {
			model.addAttribute("weather", weather);
		}
		return "weatherlist";
	}

	/**
	 * Delete city.
	 *
	 * @param model the model
	 * @param cityID the city ID
	 * @return the string
	 */
	@RequestMapping(value = "/deleteCity", method = RequestMethod.POST)
	public String deleteCity(ModelMap model, @RequestParam("cityID") Integer cityID) {
		model.addAttribute("weather", new WeatherViewModel());
		weatherService.removeWeather(cityID);
		return "weather";
	}

}
