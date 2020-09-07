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

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class WwithusEntryHistory {
	@EmbeddedId
	@EqualsAndHashCode.Include
	private Key key;

	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime dateTime;

	public User getUser() { return key.user; }
	public WwithusEntry getEntry() { return key.entry; }

	@Embeddable
	@EqualsAndHashCode(onlyExplicitlyIncluded = true)
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Builder
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
