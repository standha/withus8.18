package withus.util;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DayOfWeekTest {
	@Test
	public void testDayOfWeekInKorean() {
		List<DayOfWeek> list = new ArrayList<>();
		list.add(DayOfWeek.MONDAY);
		list.add(DayOfWeek.TUESDAY);
		list.add(DayOfWeek.FRIDAY);

		Assertions.assertThat(Utility.daysOfWeekInKorean(list)).isEqualTo("월,화,금요일");
	}
}
