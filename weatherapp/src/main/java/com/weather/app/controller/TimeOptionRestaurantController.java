package com.weather.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weather.app.service.impl.OperationHours;
import com.weather.app.util.ApplicationUtils;

@Controller
@RequestMapping("/shiftTime")
public class TimeOptionRestaurantController {

	@Autowired
	OperationHours operationHours;

	/**
	 * Calculate shitf time resturant.
	 *
	 * @return the string
	 */
	@RequestMapping(value = "/re", method = RequestMethod.GET)
	public @ResponseBody String calculateShitfTimeResturant() {
		operationHours.addShiftHour(9, 14, ApplicationUtils.MONDAY, ApplicationUtils.TUESDAY,
				ApplicationUtils.WEDNESDAY, ApplicationUtils.THURSDAY, ApplicationUtils.FRIDAY,
				ApplicationUtils.SATURDAY);
		operationHours.addShiftHour(16, 21, ApplicationUtils.MONDAY, ApplicationUtils.TUESDAY,
				ApplicationUtils.WEDNESDAY, ApplicationUtils.THURSDAY, ApplicationUtils.FRIDAY);
		operationHours.addShiftHour(12, 17, ApplicationUtils.FRIDAY);
		operationHours.addShiftHour(14, 19, ApplicationUtils.SATURDAY);

		return operationHours.printHoursShift();
	}

}
