package withus.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class WwithusEntry {
	@Id
	@Column(columnDefinition = "VARCHAR(32)", unique = true)
	@EqualsAndHashCode.Include
	private String code;

	@Column(columnDefinition = "VARCHAR(4096)", length = 4096)
	private String content;

	@OneToOne
	@JoinColumn(columnDefinition = "VARCHAR(32)")
	@Nullable
	private WwithusEntry next;

	public int getWeek() {
		Matcher matcher = createMatcherForCode(code);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		} else {
			throw new InvalidCodeFormatException(code);
		}
	}

	public int getDay() {
		Matcher matcher = createMatcherForCode(code);
		if (matcher.find()) {
			return Integer.parseInt(matcher.group(2));
		} else {
			throw new InvalidCodeFormatException(code);
		}
	}

	public String getSuffix() {
		Matcher matcher = createMatcherForCode(code);
		if (matcher.find()) {
			return matcher.group(3);
		} else {
			throw new InvalidCodeFormatException(code);
		}
	}

	public static Matcher createMatcherForCode(String code) {
		Pattern pattern = Pattern.compile("^[ \t\n]*w([0-9]+)d([0-9]+)_([0-9]+)(?:_([0-9]+?))?[ \t\n]*$", Pattern.CASE_INSENSITIVE);
		return pattern.matcher(code);
	}

	public static class InvalidCodeFormatException extends RuntimeException {
		public InvalidCodeFormatException(String code) {
			super(String.format("Code \"%s\" is not valid!", code));
		}
	}
}
