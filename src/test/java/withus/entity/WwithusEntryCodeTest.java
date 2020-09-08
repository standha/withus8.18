package withus.entity;

import java.util.regex.Matcher;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class WwithusEntryCodeTest {
	@Test
	public void testWwithusCodeFormat1() {
		String format = "W1d1_1";
		Matcher matcher = WwithusEntry.createMatcherForCode(format);
		Assertions.assertThat(matcher.find()).isTrue();

		Assertions.assertThat(matcher.group(1)).isEqualTo("1");
		Assertions.assertThat(matcher.group(2)).isEqualTo("1");
		Assertions.assertThat(matcher.group(3)).isEqualTo("1");
		Assertions.assertThat(matcher.group(4)).isNull();
	}

	@Test
	public void testWwithusCodeFormat2() {
		String format = " W2d3_1_2\t";
		Matcher matcher = WwithusEntry.createMatcherForCode(format);
		Assertions.assertThat(matcher.find()).isTrue();

		Assertions.assertThat(matcher.group(1)).isEqualTo("2");
		Assertions.assertThat(matcher.group(2)).isEqualTo("3");
		Assertions.assertThat(matcher.group(3)).isEqualTo("1");
		Assertions.assertThat(matcher.group(4)).isEqualTo("2");
	}

	@Test
	public void testWwithusCodeFormat3() {
		String format = "W1d1_1!";
		Matcher matcher = WwithusEntry.createMatcherForCode(format);
		Assertions.assertThat(matcher.find()).isFalse();
	}
}
