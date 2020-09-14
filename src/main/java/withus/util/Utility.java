package withus.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.lang.Nullable;

public class Utility {
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
	public static final String DATE_TIME_FORMAT = DATE_FORMAT + TIME_FORMAT;
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

	public static LocalDate parseDate(String dateString) { return LocalDate.parse(dateString, DATE_FORMATTER); }
	public static LocalTime parseTime(String timeString) { return LocalTime.parse(timeString, TIME_FORMATTER); }
	public static LocalDateTime parseDateTuime(String dateTimeString) { return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER); }

	public static String format(LocalDate date) { return date.format(DATE_FORMATTER); }
	public static String format(LocalTime date) { return date.format(TIME_FORMATTER); }
	public static String format(LocalDateTime date) { return date.format(DATE_TIME_FORMATTER); }

	public static boolean nullOrEmptyOrSpace(@Nullable String sentence) {
		return (sentence == null || sentence.trim().isEmpty());
	}


}
