package withus.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class WwithusEntryHistory implements Comparable<WwithusEntryHistory> {
	@EmbeddedId
	@EqualsAndHashCode.Include
	private Key key;

	@Column(columnDefinition = "TIMESTAMP")
	@Builder.Default
	private final LocalDateTime dateTime = LocalDateTime.now();

	public User getUser() { return key.user; }
	public WwithusEntry getEntry() { return key.entry; }

	@Override
	public int compareTo(@NonNull WwithusEntryHistory that) {
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
		private WwithusEntry entry;
	}
}
