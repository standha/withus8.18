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
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class WwithusEntry {
	@Id
	@Column(columnDefinition = "VARCHAR(32)", unique = true)
	@EqualsAndHashCode.Include
	private String code;

	@Column(columnDefinition = "BIT(1) NOT NULL DEFAULT FALSE")
	private boolean isAnswer;

	@Column(columnDefinition = "BIT(1) NOT NULL DEFAULT FALSE")
	private boolean isEmergencyCall;

	@Column(columnDefinition = "BIT(1) NOT NULL DEFAULT FALSE")
	private boolean isAnswerExpected;

	@Column(columnDefinition = "VARCHAR(4096)", length = 4096)
	private String content;

	@Column(columnDefinition = "VARCHAR(512)", length = 512)
	@Nullable
	private String urlToAudioFile;

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

	@NonNull
	public String getWeekAndDay() {
		return String.format("W%dD%d", getWeek(), getDay());
	}

	public String getSuffix() {
		Matcher matcher = createMatcherForCode(code);
		if (matcher.find()) {
			return matcher.group(3);
		} else {
			throw new InvalidCodeFormatException(code);
		}
	}

	@Nullable
	public String getNextCode() {
		return (next == null? null: next.code);
	}

	public static Matcher createMatcherForCode(String code) {
		Pattern pattern = Pattern.compile("^[ \t\n]*W([0-9]+)D([0-9]+)_([a-zA-Z0-9_]+?)[ \t\n]*$", Pattern.CASE_INSENSITIVE);
		return pattern.matcher(code);
	}

	public static class InvalidCodeFormatException extends RuntimeException {
		public InvalidCodeFormatException(String code) {
			super(String.format("Code \"%s\" is not valid!", code));
		}
	}
}
