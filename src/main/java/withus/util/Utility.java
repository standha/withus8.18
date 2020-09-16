package withus.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.lang.Nullable;

public class Utility {
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
	public static final String DATE_TIME_FORMAT = DATE_FORMAT + TIME_FORMAT;
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

	private static final List<DayOfWeek> WWITHUS_DAYS_OF_WEEK_ON_FIRST_TO_EIGHTH_WEEK = Stream.of(
		DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY
	).sorted().collect(Collectors.toList());

	private static final List<DayOfWeek> WWITHUS_DAYS_OF_WEEK_ON_TENTH_WEEK_OR_LATER = Stream.of(
		DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY
	).sorted().collect(Collectors.toList());

	public static LocalDate parseDate(String dateString) { return LocalDate.parse(dateString, DATE_FORMATTER); }
	public static LocalTime parseTime(String timeString) { return LocalTime.parse(timeString, TIME_FORMATTER); }
	public static LocalDateTime parseDateTuime(String dateTimeString) { return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER); }

	public static String format(LocalDate date) { return date.format(DATE_FORMATTER); }
	public static String format(LocalTime date) { return date.format(TIME_FORMATTER); }
	public static String format(LocalDateTime date) { return date.format(DATE_TIME_FORMATTER); }

	/**
	 * @return {@code week}���� {@code dayOfWeek}������ �ش� �� ������ ��ĥ�������� ����
	 */
	public static int getDayDigitForWwithus(int week, DayOfWeek dayOfWeek) {
		List<DayOfWeek> wwithusDaysOfWeek = selectDaysOfWeekList(week);

		int index = wwithusDaysOfWeek.indexOf(dayOfWeek);
		if (index < 0) {
			throw new NoWithusException(dayOfWeek, week);
		}

		return (index + 1);
	}

	/**
	 * 	 * @return {@code week}���� {@code dayOfWeek}������ ��������
	 * ���� �������� ���񽺰� �����Ǵ� ���� ���� ���������� ����
	 */
	public static DayOfWeek getNextDayForWwithus(int week, DayOfWeek dayOfWeek) {
		List<DayOfWeek> wwithusDaysOfWeek = selectDaysOfWeekList(week);

		try {
			getDayDigitForWwithus(week, dayOfWeek);

			// No exception means we have Wwithus today.
			return dayOfWeek;
		} catch (RuntimeException runtimeException) {
			DayOfWeek candidate = dayOfWeek;
			do {
				candidate = getNextDayOfWeek(candidate);
			} while (!wwithusDaysOfWeek.contains(candidate));

			return candidate;
		}
	}

	/**
	 * @return ���������� ���� �� ����ڿ��� ������ �޽���
	 */
	public static String getNonWwithusDayMessage(int week) {
		List<DayOfWeek> wwithusDaysOfWeek = selectDaysOfWeekList(week);
		String daysOfWeekInKorean = daysOfWeekInKorean(wwithusDaysOfWeek);

		return String.format(
			"�ݰ����ϴ�. ���� �������� �ڽ��� �ǰ����¸� �� ����ϰ� �����? �� ������������ ��ȭ�� %s�� �־��. �׳� �ɰԿ�.\uD83D\uDE42",
			daysOfWeekInKorean
		);
	}

	/**
	 * @return ������� ������ ���� ���������� �����ϴ� ���ϵ��� ����Ʈ.<p>
	 * {@link java.time.DayOfWeek#ordinal()} ������ ���ĵǾ� �ִ�.
	 */
	public static List<DayOfWeek> selectDaysOfWeekList(int week) {
		if (week < 1 || week > 24) { throw new RuntimeException(String.format("Unexpected value for week #: %d", week)); }

		if (week <= 8) {
			return WWITHUS_DAYS_OF_WEEK_ON_FIRST_TO_EIGHTH_WEEK;
		} else {
			return WWITHUS_DAYS_OF_WEEK_ON_TENTH_WEEK_OR_LATER;
		}
	}

	public static boolean nullOrEmptyOrSpace(@Nullable String sentence) {
		return (sentence == null || sentence.trim().isEmpty());
	}

	/**
	 * @return ������ {@link java.time.DayOfWeek}�� ���� ({@link java.time.DayOfWeek#ordinal()} ����)
	 * {@link java.time.DayOfWeek}.<p>
	 * Examples:<p>
	 * {@code getNextDayOfWeek(DayOfWeek.MONDAY)} returns {@link java.time.DayOfWeek#TUESDAY}<p>
	 * {@code getNextDayOfWeek(DayOfWeek.SUNDAY)} returns {@link java.time.DayOfWeek#MONDAY}
	 */
	public static DayOfWeek getNextDayOfWeek(DayOfWeek dayOfWeek) {
		int ordianl = dayOfWeek.ordinal();
		int nextOrdinal = ordianl + 1;
		DayOfWeek[] daysOfWeek = DayOfWeek.values();

		return daysOfWeek[nextOrdinal % daysOfWeek.length];
	}

	/**
	 * @return {@link java.time.DayOfWeek}�� {@link java.util.Locale#KOREAN}���� ǥ��
	 */
	public static String dayOfWeekInKorean(DayOfWeek dayOfWeek) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E", Locale.KOREAN);
		return dateTimeFormatter.format(dayOfWeek);
	}

	/**
	 * @return {@link java.util.Collection} of {@link java.time.DayOfWeek}��
	 * {@link java.util.Locale#KOREAN}���� ǥ��<p>
	 * Example: "��,ȭ,������"
	 */
	public static String daysOfWeekInKorean(Collection<DayOfWeek> daysOfWeek) {
		List<String> strings = new ArrayList<>();
		for (DayOfWeek dayOfWeek : daysOfWeek) {
			strings.add(dayOfWeekInKorean(dayOfWeek));
		}

		return (String.join(",", strings) + "����");
	}

	/**
	 * ���������� ���� ������ �˸��� ���� {@link java.lang.RuntimeException}.
	 */
	public static class NoWithusException extends RuntimeException {
		public NoWithusException(DayOfWeek dayOfWeek, int week) {
			super(String.format("No Wwithus on %s week #%d.", dayOfWeek, week));
		}
	}
}
