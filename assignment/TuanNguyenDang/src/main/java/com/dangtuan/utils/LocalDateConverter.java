package com.dangtuan.utils;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The Class LocalDateConverter.
 * 
 * @author tuannguyen
 */
public class LocalDateConverter implements Converter<String, LocalDate> {

	/** The formatter. */
	private final DateTimeFormatter formatter;

	/**
	 * Instantiates a new local date converter.
	 *
	 * @param dateFormat the date format
	 */
	public LocalDateConverter(String dateFormat) {
		this.formatter = DateTimeFormatter.ofPattern(dateFormat);
	}

	/* (non-Javadoc)
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public LocalDate convert(String source) {
		if (source == null || source.isEmpty()) {
			return null;
		}

		return LocalDate.parse(source, formatter);
	}
}
