package withus.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class WwithusEntryCaregiver implements Comparable<WwithusEntryCaregiver> {
    @Id
    @Column(columnDefinition = "VARCHAR(32)", unique = true)
    @EqualsAndHashCode.Include
    private String code;

    /**
     * 시작 엔트리인지 여부
     */
    @Column(name = "is_first", columnDefinition = "BIT(1) NOT NULL DEFAULT FALSE COMMENT '시작 엔트리인지 여부'")
    private boolean first;

    /**
     * 종료 엔트리인지 여부
     */
    @Column(name = "is_last", columnDefinition = "BIT(1) NOT NULL DEFAULT FALSE COMMENT '종료 엔트리인지 여부'")
    private boolean last;

    /**
     * 해당 선택지를 택하였을 때 내역을 삭제하고 위더스랑을 처음부터 재시작하고자 한다면 {@code true}, {@code false} otherwise.
     */
    @Column(name = "is_to_rewind", columnDefinition = "BIT(1) NOT NULL DEFAULT FALSE COMMENT '해당 선택지를 택하였을 때 내역을 삭제하고 위더스랑을 처음부터 재시작하고자 한다면 TRUE, FALSE otherwise.'")
    private boolean toRewind;

    /**
     * 사용자의 선택지인지 여부
     */
    @Column(name = "is_answer", columnDefinition = "BIT(1) NOT NULL DEFAULT FALSE COMMENT '사용자의 선택지인지 여부'")
    private boolean answer;

    /**
     * 위더스 도우미 호출인지 여부
     */
    @Column(name = "is_help_request", columnDefinition = "BIT(1) NOT NULL DEFAULT FALSE COMMENT '위더스 도우미 호출인지 여부'")
    private boolean helpRequest;

    /**
     * 답변 필요 여부
     */
    @Column(name = "is_answer_expected", columnDefinition = "BIT(1) NOT NULL DEFAULT FALSE COMMENT '답변 필요 여부'")
    private boolean answerExpected;

    @Column(columnDefinition = "VARCHAR(4096)", length = 4096)
    private String content;

    @Column(columnDefinition = "VARCHAR(512)", length = 512)
    @Nullable
    private String urlToImageFile;

    @Column(columnDefinition = "VARCHAR(512)", length = 512)
    @Nullable
    private String urlToAudioFile;

    @OneToOne
    @JoinColumn(columnDefinition = "VARCHAR(32) COMMENT '다음 statement의 CODE'")
    @NotFound(action = NotFoundAction.IGNORE)
    @Nullable
    private WwithusEntryCaregiver next;

    @Override
    public int compareTo(@NonNull WwithusEntryCaregiver that) {
        return code.compareTo(that.code);
    }

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

    @Nullable
    public String getNextCode() {
        return (next == null ? null : next.code);
    }

    public static Matcher createMatcherForCode(String code) {
        /*
         * regular expressions로 곤란을 겪고 있다면?
         * https://regex101.com/r/qAg8q5/3
         */
        Pattern pattern = Pattern.compile("^[ \t\n]*CW([0-9]+)D([0-9]+)_([a-zA-Z0-9_]+?)[ \t\n]*$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(code);
    }

    public static class InvalidCodeFormatException extends RuntimeException {
        public InvalidCodeFormatException(String code) {
            super(String.format("Code \"%s\" is not valid!", code));
        }
    }
}
