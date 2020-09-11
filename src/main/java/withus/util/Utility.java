package withus.util;

import java.time.DayOfWeek;
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

	/**
	 * @return {@code week}주차 {@code dayOfWeek}요일이 해당 주 내에서 며칠차인지를 리턴
	 */
	public static int getDayDigitForWwithus(int week, DayOfWeek dayOfWeek) {
		if (week < 1 || week > 24) { throw new RuntimeException(String.format("Unexpected value for week #: %d", week)); }

		if (week <= 8) {
			switch (dayOfWeek) {
				case MONDAY:
					return 1;
				case TUESDAY:
					return 2;
				case THURSDAY:
					return 3;
				case SATURDAY:
					return 4;
				default:
					throw new RuntimeException(String.format("No Wwithus on %s week #%d.", dayOfWeek, week));
			}
		} else {
			switch (dayOfWeek) {
				case MONDAY:
					return 1;
				case WEDNESDAY:
					return 2;
				case SATURDAY:
					return 3;
				default:
					throw new RuntimeException(String.format("No Wwithus on %s week #%d.", dayOfWeek, week));
			}
		}
	}

	/**
	 * @return {@code week}주차 {@code dayOfWeek}요일을 기준으로
	 * 다음 위더스랑 서비스가 제공되는 것이 무슨 요일인지를 리턴
	 */
	public static DayOfWeek getNextDayForWwithus(int week, DayOfWeek dayOfWeek) {
		if (week < 1 || week > 24) { throw new RuntimeException(String.format("Unexpected value for week #: %d", week)); }

		try {
			getDayDigitForWwithus(week, dayOfWeek);

			// No exception means we have Wwithus today.
			return dayOfWeek;
		} catch (RuntimeException runtimeException) {
			if (week <= 8) {
				switch (dayOfWeek) {
					case SUNDAY:
					case MONDAY:
						return DayOfWeek.MONDAY;
					case TUESDAY:
						return DayOfWeek.TUESDAY;
					case WEDNESDAY:
					case THURSDAY:
						return DayOfWeek.THURSDAY;
					case FRIDAY:
					case SATURDAY:
						return DayOfWeek.SATURDAY;
				}
			} else {
				switch (dayOfWeek) {
					case SUNDAY:
					case MONDAY:
						return DayOfWeek.MONDAY;
					case TUESDAY:
					case WEDNESDAY:
						return DayOfWeek.WEDNESDAY;
					case THURSDAY:
					case FRIDAY:
					case SATURDAY:
						return DayOfWeek.SATURDAY;
				}
			}

			throw new RuntimeException("We are sorry. The Earth is going to explode now.");
		}
	}

	public static boolean nullOrEmptyOrSpace(@Nullable String sentence) {
		return (sentence == null || sentence.trim().isEmpty());
	}
}
