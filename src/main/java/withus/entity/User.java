package withus.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(indexes = @Index(columnList = "id,password"))
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Setter
@ToString
public class User implements Serializable, UserDetails {
	@Id
	@Column(name = "id", columnDefinition = "VARCHAR(128) NOT NULL", length = 128, unique = true)
	@EqualsAndHashCode.Include
	@Getter
	private String userId;

	@Column(columnDefinition = "VARCHAR(255) NOT NULL")
	private String password;

	@Column(columnDefinition = "VARCHAR(32) NOT NULL", length = 32)
	@Getter
	private String name;

	@Column(columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
	@Builder.Default
	@Getter
	private LocalDateTime registrationDateTime = LocalDateTime.now();

	@Column(columnDefinition = "VARCHAR(32) NOT NULL", length = 32, unique = true)
	@NonNull
	@Getter
	private String contact;

	@Column(columnDefinition = "DATE")
	@Nullable
	@Getter
	private LocalDate birthdate;

	@Column(columnDefinition = "VARCHAR(6)", length = 6)
	@Enumerated(EnumType.STRING)
	@Nullable
	@Getter
	private Gender gender;

	@Column(columnDefinition = "VARCHAR(256)", length = 256)
	@Nullable
	@Getter
	private String appToken;

	@Column(columnDefinition = "VARCHAR(16) NOT NULL", length = 16)
	@Enumerated(EnumType.STRING)
	@Getter
	private Type type;

	@Column(columnDefinition = "INT(3) NOT NULL DEFAULT 0")
	@Getter
	private int week;

	@OneToOne
	@JoinColumn(name = "caregiver_contact", columnDefinition = "VARCHAR(32)", referencedColumnName = "contact")
	@Nullable
	@Getter
	private User caregiver;

	@Column(name = "week")
	@Getter
	private Integer week;

	@Column(name = "level")
	@Getter
	private Integer level;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}
	@Override
	public String getPassword() { return password; }
	@Override
	public String getUsername() { return userId; }
	@Override
	public boolean isAccountNonExpired() { return true; }
	@Override
	public boolean isAccountNonLocked() { return true; }
	@Override
	public boolean isCredentialsNonExpired() { return true; }
	@Override
	public boolean isEnabled() { return true; }

	public enum Gender {
		MALE, FEMALE
	}

	public enum Type {
		PATIENT, CAREGIVER, ADMINISTRATOR
	}
}
