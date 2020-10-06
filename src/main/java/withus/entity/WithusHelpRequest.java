package withus.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = {
        @Index(columnList = "dateTime"),
        @Index(columnList = "dealtWith")
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class WithusHelpRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private int requestId;

    @Column(columnDefinition = "TIMESTAMP")
    @Builder.Default
    private final LocalDateTime dateTime = LocalDateTime.now();

    /**
     * 위더스 도우미 호출 처리가 완료되었는지 (호출한 환자와의 면담 완료 등)
     */
    @Column(columnDefinition = "BIT(1) NOT NULL DEFAULT FALSE COMMENT '위더스 도우미 호출 처리가 완료되었는지 여부 (호출한 환자와의 면담 완료 등)'")
    private boolean dealtWith;

    @OneToOne
    @JoinColumn(columnDefinition = "VARCHAR(128) NOT NULL")
    private User user;

    @Column(name = "helpCode")
    private String helpCode;
}
