package withus.entity;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class WwithusEntryHistoryCaregiver implements Comparable<WwithusEntryHistoryCaregiver> {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private Key key;

    @Column(columnDefinition = "TIMESTAMP")
    @Builder.Default
    private final LocalDateTime dateTime = LocalDateTime.now();

    public User getUser() {
        return key.user;
    }

    public WwithusEntryCaregiver getEntry() {
        return key.entry;
    }

    @Override
    public int compareTo(@NonNull WwithusEntryHistoryCaregiver that) {
        return dateTime.compareTo(that.dateTime);
    }

    @Embeddable
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Getter
    @ToString
    public static class Key implements Serializable {
        @OneToOne
        @JoinColumn(columnDefinition = "VARCHAR(128) NOT NULL")
        @EqualsAndHashCode.Include
        private User user;

        @OneToOne
        @JoinColumn(columnDefinition = "VARCHAR(32)")
        @EqualsAndHashCode.Include
        private WwithusEntryCaregiver entry;
    }
}
