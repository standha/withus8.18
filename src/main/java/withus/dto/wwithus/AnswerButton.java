package withus.dto.wwithus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class AnswerButton implements Comparable<AnswerButton> {
	private final int ordinal;
	private final boolean isToTerminate;
	private final String content;
	@Nullable
	private final String urlToImageFile;
	@Nullable
	private final String nextCode;

	@Override
	public int compareTo(@NonNull AnswerButton that) {
		return Integer.compare(ordinal, that.ordinal);
	}
}
