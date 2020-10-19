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
            DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY
    ).sorted().collect(Collectors.toList());

    public static LocalDate parseDate(String dateString) {
        if(dateString.equals("null")){
            return null;
        }
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }

    public static LocalTime parseTime(String timeString) {
        return LocalTime.parse(timeString, TIME_FORMATTER);
    }

    public static LocalDateTime parseDateTuime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
    }

    public static String format(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String format(LocalTime date) {
        return date.format(TIME_FORMATTER);
    }

    public static String format(LocalDateTime date) {
        return date.format(DATE_TIME_FORMATTER);
    }

    /**
     * @return {@code week}주차 {@code dayOfWeek}요일이 해당 주 내에서 며칠차인지를 리턴
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
     * @return {@code week}주차 {@code dayOfWeek}요일을 기준으로
     * 다음 위더스랑 서비스가 제공되는 것이 무슨 요일인지를 리턴
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
     * @return 위더스랑이 없는 날 사용자에게 제공할 메시지
     */
    public static String getNonWwithusDayMessage(int week) {
        List<DayOfWeek> wwithusDaysOfWeek = selectDaysOfWeekList(week);
        String daysOfWeekInKorean = daysOfWeekInKorean(wwithusDaysOfWeek);

        return String.format(
                "반갑습니다. 매일 위더스에 자신의 건강상태를 잘 기록하고 계시죠? 저 위더스랑과의 대화는 %s에 있어요. 그날 뵐게요.\uD83D\uDE42",
                daysOfWeekInKorean
        );
    }

    /**
     * @return 사용자의 주차에 따라 위더스랑을 진행하는 요일들의 리스트.<p>
     * {@link java.time.DayOfWeek#ordinal()} 순으로 정렬되어 있다.
     */
    public static List<DayOfWeek> selectDaysOfWeekList(int week) {
        if (week < 1 || week > 24) {
            throw new RuntimeException(String.format("Unexpected value for week #: %d", week));
        }

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
     * @return 지정한 {@link java.time.DayOfWeek}의 다음 ({@link java.time.DayOfWeek#ordinal()} 기준)
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
     * @return {@link java.time.DayOfWeek}를 {@link java.util.Locale#KOREAN}으로 표현
     */
    public static String dayOfWeekInKorean(DayOfWeek dayOfWeek) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E", Locale.KOREAN);
        return dateTimeFormatter.format(dayOfWeek);
    }

    /**
     * @return {@link java.util.Collection} of {@link java.time.DayOfWeek}를
     * {@link java.util.Locale#KOREAN}으로 표현<p>
     * Example: "월,화,수요일"
     */
    public static String daysOfWeekInKorean(Collection<DayOfWeek> daysOfWeek) {
        List<String> strings = new ArrayList<>();
        for (DayOfWeek dayOfWeek : daysOfWeek) {
            strings.add(dayOfWeekInKorean(dayOfWeek));
        }

        return (String.join(",", strings) + "요일");
    }

    /**
     * 위더스랑이 없는 날임을 알리기 위한 {@link java.lang.RuntimeException}.
     */
    public static class NoWithusException extends RuntimeException {
        public NoWithusException(DayOfWeek dayOfWeek, int week) {
            super(String.format("No Wwithus on %s week #%d.", dayOfWeek, week));
        }
    }

    public static class NoHisException extends RuntimeException {
        public NoHisException() {
            super("No Wwithus history of Caregiver .");
        }
    }
}
