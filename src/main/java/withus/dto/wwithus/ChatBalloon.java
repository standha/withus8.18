package withus.dto.wwithus;

import java.time.LocalDateTime;
import java.util.Comparator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ChatBalloon implements Comparable<ChatBalloon> {
	private final Direction direction;
	private final String content;
	private final LocalDateTime dateTime;

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
