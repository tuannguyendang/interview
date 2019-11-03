package com.weather.app.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The Class ApplicationUtils.
 */
public class ApplicationUtils {
	private static double KC_CHANGE = -273.15;
	public static final String WEATHER_CITY_URL = "http://api.openweathermap.org/data/2.5/weather?q={city}&APPID={key}";
	public static final int MONDAY = 0, TUESDAY = 1, WEDNESDAY = 2, THURSDAY = 3, FRIDAY = 4, SATURDAY = 5, SUNDAY = 6;
	public static final String[] WEEKDAYS = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday",
			"Sunday" };
	public static final String FORMAT_DATE = "MMM d,yyyy";

	/**
	 * Change temperature KC.
	 *
	 * @param kDegree the k degree
	 * @return the string
	 */
	public static String changeTemperatureKC(double kDegree) {
		DecimalFormat df = new DecimalFormat("###.####");
		return df.format(kDegree + KC_CHANGE);
	}

	/**
	 * Date format.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String dateFormat(Date date) {
		DateFormat format = new SimpleDateFormat(FORMAT_DATE);
		return format.format(date);
	}

	/**
	 * Convert string to date.
	 *
	 * @param dateString the date string
	 * @return the date
	 * @throws ParseException the parse exception
	 */
	public static Date convertStringToDate(String dateString) throws ParseException {
		DateFormat df = new SimpleDateFormat(FORMAT_DATE);
		Date date = df.parse(dateString);
		return date;
	}

	/**
	 * Gets the formatted from date time.
	 *
	 * @param date the date
	 * @return the formatted from date time
	 */
	public static Date getFormattedFromDateTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
}
