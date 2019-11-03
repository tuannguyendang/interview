package com.weather.app.service.impl;

import org.springframework.stereotype.Service;

import com.weather.app.util.ApplicationUtils;

/**
 * The Class OperationHours.
 */
@Service
public class OperationHours {

	/** The hours. */
	int[] hours = new int[7];

	/**
	 * Adds the shift hour.
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @param days
	 *            the days
	 */
	public void addShiftHour(final int start, final int end, final int... days) {
		for (int i = start; i <= end; i++) {
			for (final int w : days) {
				// add all open hour to one number use | bit operator
				// example : 9 hour will: 100000000 and 10 hour will:
				// 100000000000 - result or will: 11000000000 with value
				// 1 mean open, 0 mean close.
				hours[w] |= 1 << i;
			}
		}
	}

	/**
	 * Prints the hours shift.
	 *
	 * @return the string
	 */
	public String printHoursShift() {
		final StringBuilder sb = new StringBuilder();
		// calculate hours open of first day
		String prevDay = getShiftTimeInDay(hours[0]);
		String startDay = ApplicationUtils.WEEKDAYS[0];
		String endDay = ApplicationUtils.WEEKDAYS[0];
		// repeate calculate open hour for another day
		for (int i = 1; i < 7; i++) {
			final String day = getShiftTimeInDay(hours[i]);
			// if open hour same with previous day, set end day
			if (day.equals(prevDay))
				endDay = ApplicationUtils.WEEKDAYS[i];
			// if not add new line to result
			else {
				appendDayToResult(sb, startDay, endDay, prevDay);
				prevDay = day;
				startDay = endDay = ApplicationUtils.WEEKDAYS[i];
			}
		}
		appendDayToResult(sb, startDay, endDay, prevDay);
		return sb.toString();
	}

	/**
	 * Gets the shift time in day.
	 *
	 * @param m
	 *            the m
	 * @return the shift time in day
	 */
	String getShiftTimeInDay(final int m) {
		final StringBuilder sb = new StringBuilder();
		int startHour = -1;
		int endHour = -1;
		// loop in 24 hours and use & bit operation to find the time open or
		// not
		for (int i = 0; i <= 24; i++) {
			// use bit & operation check this time open or not: 1&1->1
			// 1&0->0. 1 is open, 0 is close
			if (((1 << i) & m) > 0) {
				if (startHour == -1)
					startHour = i;
				endHour = i;
			}
			// append to result and reset when time close
			else if (startHour >= 0) {
				sb.append("\t" + formatHourShift(startHour) + " to " + formatHourShift(endHour) + "\n<br>");
				startHour = endHour = -1;
			}
		}
		// in case open all date
		if (startHour >= 0)
			sb.append("\t" + formatHourShift(startHour) + " to " + formatHourShift(endHour) + "\n");
		return sb.toString();
	}

	/**
	 * Append day to result.
	 *
	 * @param sb
	 *            the sb
	 * @param startDay
	 *            the start day
	 * @param endDay
	 *            the end day
	 * @param prevDay
	 *            the prev day
	 */
	void appendDayToResult(final StringBuilder sb, final String startDay, final String endDay, final String prevDay) {
		sb.append(startDay + (startDay.equals(endDay) ? "" : ("-" + endDay)) + "\n"
				+ (prevDay.equals("") ? "\tClosed\n" : prevDay));
	}

	/**
	 * Format hour shift.
	 *
	 * @param h
	 *            the h
	 * @return the string
	 */
	String formatHourShift(final int h) {
		if (h == 0)
			return "12AM";
		if (h < 12)
			return h + "AM";
		if (h == 12)
			return "12PM";
		if (h < 24)
			return (h - 12) + "PM";
		// 24 hours is midnight again
		return "12AM";
	}
}
