package withus.dto.wwithus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private final String code;
    private final Direction direction;
    private final boolean isMostRecent;
    private final boolean isToTerminate;
    private final boolean isAnswerExpected;
    private final String content;
    @Nullable
    private final String urlToImageFile;
    @Nullable
    private final String urlToAudioFile;
    @Builder.Default
    private final LocalDateTime dateTime = LocalDateTime.now();
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

    @JsonProperty("containsFinishingAnswer")
    public boolean containsFinishingAnswer() {
        return answerButtons.stream().anyMatch(AnswerButton::isToTerminate);
    }

    public enum Direction {
        LEFT, RIGHT;
    }
}
