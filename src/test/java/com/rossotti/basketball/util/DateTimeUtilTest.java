package com.rossotti.basketball.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;

public class DateTimeUtilTest {

	@Test
	public void getStringDate_FromDate() {
		String date = DateTimeUtil.getStringDate(new LocalDate(2013, 3, 30));
		Assert.assertEquals("2013-03-30", date);
	}

	@Test
	public void getStringDate_FromDateTime() {
		String dateTime = DateTimeUtil.getStringDate(new LocalDateTime(2013, 3, 30, 10, 30));
		Assert.assertEquals("2013-03-30", dateTime);
	}

	@Test
	public void getStringDateTime() {
		String dateTime = DateTimeUtil.getStringDateTime(new LocalDateTime(2013, 3, 30, 10, 30));
		Assert.assertEquals("2013-03-30T10:30", dateTime);
	}

	@Test
	public void getStringDateNaked_FromDateTime() {
		String dateTime = DateTimeUtil.getStringDateNaked(new LocalDateTime(2013, 3, 30, 10, 30));
		Assert.assertEquals("20130330", dateTime);
	}

	@Test
	public void getStringDateNaked_FromDate() {
		String date = DateTimeUtil.getStringDateNaked(new LocalDate(2013, 3, 30));
		Assert.assertEquals("20130330", date);
	}

	@Test
	public void getLocalDate_FromString() {
		LocalDate date = DateTimeUtil.getLocalDate("2014-06-30");
		Assert.assertEquals(new LocalDate("2014-06-30"), date);
	}

	@Test
	public void getLocalDate_FromLocalDateTime() {
		LocalDateTime dateTime = DateTimeUtil.getLocalDateTime("2014-06-30T10:30");
		Assert.assertEquals(new LocalDate("2014-06-30"), DateTimeUtil.getLocalDate(dateTime));
	}

	@Test
	public void getLocalDate_FromDateTime() {
		DateTime dateTime = new DateTime("2016-03-11T19:30:00-05:00");
		Assert.assertEquals(new LocalDate("2016-03-11"), DateTimeUtil.getLocalDate(dateTime));
	}

	@Test
	public void getLocalDateTime() {
		LocalDateTime dateTime = DateTimeUtil.getLocalDateTime("2014-06-30T10:30");
		Assert.assertEquals(new LocalDateTime("2014-06-30T10:30"), dateTime);
	}
	
	@Test
	public void getLocalDateTimeMin() {
		LocalDateTime dateTime = DateTimeUtil.getLocalDateTimeMin(new LocalDate("2014-06-30"));
		Assert.assertEquals(new LocalDateTime("2014-06-30T00:00"), dateTime);
	}

	@Test
	public void getLocalDateTimeMax() {
		LocalDateTime dateTime = DateTimeUtil.getLocalDateTimeMax(new LocalDate("2014-06-30"));
		Assert.assertEquals(new LocalDateTime("2014-06-30T23:59"), dateTime);
	}

	@Test
	public void getLocalDateSeasonMin_SeasonStart() {
		LocalDate date = DateTimeUtil.getLocalDateSeasonMin(new LocalDate("2013-07-01"));
		Assert.assertEquals(new LocalDate("2013-07-01"), date);
	}
	@Test
	public void getLocalDateSeasonMin_YearEnd() {
		LocalDate date = DateTimeUtil.getLocalDateSeasonMin(new LocalDate("2013-12-31"));
		Assert.assertEquals(new LocalDate("2013-07-01"), date);
	}
	@Test
	public void getLocalDateSeasonMin_YearStart() {
		LocalDate date = DateTimeUtil.getLocalDateSeasonMin(new LocalDate("2014-01-01"));
		Assert.assertEquals(new LocalDate("2013-07-01"), date);
	}
	@Test
	public void getLocalDateSeasonMin_SeasonEnd() {
		LocalDate date = DateTimeUtil.getLocalDateSeasonMin(new LocalDate("2014-06-30"));
		Assert.assertEquals(new LocalDate("2013-07-01"), date);
	}

	@Test
	public void getLocalDateSeasonMax_SeasonStart() {
		LocalDate date = DateTimeUtil.getLocalDateSeasonMax(new LocalDate("2014-07-01"));
		Assert.assertEquals(new LocalDate("2015-06-30"), date);
	}
	@Test
	public void getLocalDateSeasonMax_YearEnd() {
		LocalDate date = DateTimeUtil.getLocalDateSeasonMax(new LocalDate("2014-12-31"));
		Assert.assertEquals(new LocalDate("2015-06-30"), date);
	}
	@Test
	public void getLocalDateSeasonMax_YearStart() {
		LocalDate date = DateTimeUtil.getLocalDateSeasonMax(new LocalDate("2015-01-01"));
		Assert.assertEquals(new LocalDate("2015-06-30"), date);
	}
	@Test
	public void getLocalDateSeasonMax_SeasonEnd() {
		LocalDate date = DateTimeUtil.getLocalDateSeasonMax(new LocalDate("2015-06-30"));
		Assert.assertEquals(new LocalDate("2015-06-30"), date);
	}

	@Test
	public void getLocalDateTimeSeasonMin_SeasonStart() {
		LocalDateTime dateTime = DateTimeUtil.getLocalDateTimeSeasonMin(new LocalDate("2013-07-01"));
		Assert.assertEquals(new LocalDateTime("2013-07-01T00:00"), dateTime);
	}
	@Test
	public void getLocalDateTimeSeasonMin_YearEnd() {
		LocalDateTime dateTime = DateTimeUtil.getLocalDateTimeSeasonMin(new LocalDate("2013-12-31"));
		Assert.assertEquals(new LocalDateTime("2013-07-01T00:00"), dateTime);
	}
	@Test
	public void getLocalDateTimeSeasonMin_YearStart() {
		LocalDateTime dateTime = DateTimeUtil.getLocalDateTimeSeasonMin(new LocalDate("2014-01-01"));
		Assert.assertEquals(new LocalDateTime("2013-07-01T00:00"), dateTime);
	}
	@Test
	public void getLocalDateTimeSeasonMin_SeasonEnd() {
		LocalDateTime dateTime = DateTimeUtil.getLocalDateTimeSeasonMin(new LocalDate("2014-06-30"));
		Assert.assertEquals(new LocalDateTime("2013-07-01T00:00"), dateTime);
	}

	@Test
	public void getLocalDateTimeSeasonMax_SeasonStart() {
		LocalDateTime dateTime = DateTimeUtil.getLocalDateTimeSeasonMax(new LocalDate("2014-07-01"));
		Assert.assertEquals(new LocalDateTime("2015-06-30T23:59"), dateTime);
	}
	@Test
	public void getLocalDateTimeSeasonMax_YearEnd() {
		LocalDateTime dateTime = DateTimeUtil.getLocalDateTimeSeasonMax(new LocalDate("2014-12-31"));
		Assert.assertEquals(new LocalDateTime("2015-06-30T23:59"), dateTime);
	}
	@Test
	public void getLocalDateTimeSeasonMax_YearStart() {
		LocalDateTime dateTime = DateTimeUtil.getLocalDateTimeSeasonMax(new LocalDate("2015-01-01"));
		Assert.assertEquals(new LocalDateTime("2015-06-30T23:59"), dateTime);
	}
	@Test
	public void getLocalDateTimeSeasonMax_SeasonEnd() {
		LocalDateTime dateTime = DateTimeUtil.getLocalDateTimeSeasonMax(new LocalDate("2015-06-30"));
		Assert.assertEquals(new LocalDateTime("2015-06-30T23:59"), dateTime);
	}

	@Test
	public void calculateDateDiff_23Hours() {
		LocalDateTime minDate = new LocalDateTime("2013-03-31T19:00");
		LocalDateTime maxDate = new LocalDateTime("2013-04-01T18:00");
		int days = DateTimeUtil.getDaysBetweenTwoDateTimes(minDate, maxDate);
		Assert.assertEquals(0, days);
	}

	@Test
	public void calculateDateDiff_25Hours() {
		LocalDateTime minDate = new LocalDateTime("2013-03-31T19:00");
		LocalDateTime maxDate = new LocalDateTime("2013-04-01T20:00");
		int days = DateTimeUtil.getDaysBetweenTwoDateTimes(minDate, maxDate);
		Assert.assertEquals(1, days);
	}

	@Test
	public void calculateDateDiff_Over30Days() {
		LocalDateTime minDate = new LocalDateTime("2013-03-31T19:00");
		LocalDateTime maxDate = new LocalDateTime("2013-06-01T20:00");
		int days = DateTimeUtil.getDaysBetweenTwoDateTimes(minDate, maxDate);
		Assert.assertEquals(0, days);
	}

	@Test
	public void calculateDateDiff_NullMinDate() {
		LocalDateTime minDate = null;
		LocalDateTime maxDate = new LocalDateTime("2013-06-01T20:00");
		int days = DateTimeUtil.getDaysBetweenTwoDateTimes(minDate, maxDate);
		Assert.assertEquals(0, days);
	}

	@Test
	public void createDateMinusOneDay_EndOfMonth() {
		LocalDate date = DateTimeUtil.getDateMinusOneDay(new LocalDate("2013-06-30"));
		Assert.assertEquals(new LocalDate(2013, 6, 29), date);
	}
	@Test
	public void createDateMinusOneDay_BeginingOfMonth() {
		LocalDate date = DateTimeUtil.getDateMinusOneDay(new LocalDate("2013-07-01"));
		Assert.assertEquals(new LocalDate(2013, 6, 30), date);
	}

	@Test
	public void isDate_PastDate() {
		Assert.assertTrue(DateTimeUtil.isDate("1969-12-31"));
	}
	@Test
	public void isDate_FutureDate() {
		Assert.assertTrue(DateTimeUtil.isDate("2069-12-31"));
	}
	@Test
	public void isDate_InvalidDate() {
		Assert.assertFalse(DateTimeUtil.isDate("2069-12"));
	}
}
