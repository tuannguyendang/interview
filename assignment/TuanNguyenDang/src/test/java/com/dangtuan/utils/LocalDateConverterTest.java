package com.dangtuan.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalDateConverterTest {
	LocalDateConverter localDateConverter = new LocalDateConverter("yyyy-MM-dd");

	@Test
	public void convertWhenInputValidWillReturnValue() {
		String source = "2017-04-01";
		LocalDate localDate = localDateConverter.convert(source);
		Assert.assertNotNull(localDate);
		Assert.assertEquals(localDate.getYear(), 2017);
		Assert.assertEquals(localDate.getMonthValue(), 4);
		Assert.assertEquals(localDate.getDayOfMonth(), 1);
	}

	@Test(expected = DateTimeParseException.class)
	public void convertWhenInputInValidMonthWillReturnDateTimeParseException() {
		String source = "2017-13-01";
		localDateConverter.convert(source);
	}

	@Test(expected = DateTimeParseException.class)
	public void convertWhenInputInValidDayWillReturnDateTimeParseException() {
		String source = "2017-12-41";
		localDateConverter.convert(source);
	}

	@Test(expected = DateTimeParseException.class)
	public void convertWhenInputStringWillReturnDateTimeParseException() {
		String source = "2017-aaa-01";
		localDateConverter.convert(source);
	}
	
	@Test(expected = DateTimeParseException.class)
	public void convertWhenInputEmptyWillReturnDateTimeParseException() {
		String source = " ";
		localDateConverter.convert(source);
	}
}
