package com.bluecore.withus.util;

import java.time.format.DateTimeFormatter;

public class Utility {
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
	public static final String TIME_FORMAT = "hh:mm:ss";
	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
	public static final String DATE_TIME_FORMAT = DATE_FORMAT + TIME_FORMAT;
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
}
