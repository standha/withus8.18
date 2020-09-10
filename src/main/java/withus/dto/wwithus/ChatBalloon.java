package withus.dto.wwithus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ChatBalloon implements Comparable<ChatBalloon> {
	private final Direction direction;
	private final boolean isMostRecent;
	private final boolean isHelpRequest;
	private final boolean isAnswerExpected;
	private final String content;
	@Nullable
	private final String urlToImageFile;
	@Nullable
	private final String urlToAudioFile;
	private final LocalDateTime dateTime;
	@Nullable
	private final String nextCode;
	@NonNull
	@Builder.Default
	private final List<AnswerButton> answerButtons = new ArrayList<>();

	@Override
	public int compareTo(@NonNull ChatBalloon that) {
		return Comparator.comparing(ChatBalloon::getDateTime)
			.thenComparing(ChatBalloon::getDirection)
			.compare(this, that);
	}

	public enum Direction {
		LEFT, RIGHT;
	}
}
