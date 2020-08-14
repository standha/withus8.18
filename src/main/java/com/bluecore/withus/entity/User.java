package com.bluecore.withus.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(indexes = @Index(columnList = "id,password"))
public class User implements Serializable, UserDetails {
	@Id
	@Column(columnDefinition = "VARCHAR(128) NOT NULL", length = 128)
	@NonNull
	protected String id;

	@Column(columnDefinition = "VARCHAR(255) NOT NULL")
	private String password;

	@Column(columnDefinition = "INT(11)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer ordinal;

	@Column(columnDefinition = "VARCHAR(32) NOT NULL", length = 32)
	private String name;

	@Column(columnDefinition = "VARCHAR(32) NOT NULL", length = 32, unique = true)
	@NonNull
	private String contact;

	@Column(columnDefinition = "DATE")
	@Nullable
	private LocalDate birthdate;

	@Column(columnDefinition = "VARCHAR(6)", length = 6)
	@Enumerated(EnumType.STRING)
	@Nullable
	private Gender gender;

	@Column(columnDefinition = "VARCHAR(16) NOT NULL", length = 16)
	@Enumerated(EnumType.STRING)
	private Type type;

	@OneToOne
	@JoinColumn(name = "caregiver_contact", columnDefinition = "VARCHAR(32)", referencedColumnName = "contact")
	@Nullable
	private User caregiver;

	public User() { }
	private User(@NonNull String id, String password, Integer ordinal, String name, @NonNull String contact, @Nullable LocalDate birthdate, @Nullable Gender gender, Type type, @Nullable User caregiver) {
		this.id = id;
		this.password = password;
		this.ordinal = ordinal;
		this.name = name;
		this.contact = contact;
		this.birthdate = birthdate;
		this.gender = gender;
		this.type = type;
		this.caregiver = caregiver;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		User user = (User)o;
		return id.equals(user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}
	@Override
	public String getPassword() { return password; }
	@Override
	public String getUsername() { return id; }
	@Override
	public boolean isAccountNonExpired() { return true; }
	@Override
	public boolean isAccountNonLocked() { return true; }
	@Override
	public boolean isCredentialsNonExpired() { return true; }
	@Override
	public boolean isEnabled() { return true; }

	@NonNull
	public String getId() { return id; }

	public Integer getOrdinal() { return ordinal; }
	public String getName() { return name; }
	@NonNull
	public String getContact() { return contact; }
	@Nullable
	public LocalDate getBirthdate() { return birthdate; }
	@Nullable
	public Gender getGender() { return gender; }
	public Type getType() { return type; }
	@Nullable
	public User getCaregiver() { return caregiver; }

	public void setPassword(String password) { this.password = password; }
	public void setCaregiver(@Nullable User user) { this.caregiver = user;}

	public enum Gender {
		MALE, FEMALE
	}

	public enum Type {
		PATIENT, CAREGIVER, ADMINISTRATOR
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String id;
		private String password;
		private Integer ordinal;
		private String name;
		private String contact;
		private LocalDate birthdate;
		private Gender gender;
		private Type type;
		private User caregiver;

		public Builder setId(String id) {
			this.id = id;
			return this;
		}
		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}
		public Builder setOrdinal(Integer ordinal) {
			this.ordinal = ordinal;
			return this;
		}
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		public Builder setContact(String contact) {
			this.contact = contact;
			return this;
		}
		public Builder setBirthdate(LocalDate birthdate) {
			this.birthdate = birthdate;
			return this;
		}
		public Builder setGender(Gender gender) {
			this.gender = gender;
			return this;
		}
		public Builder setType(Type type) {
			this.type = type;
			return this;
		}
		public Builder setCaregiver(User caregiver) {
			this.caregiver = caregiver;
			return this;
		}

		public User createUser() {
			return new User(id, password, ordinal, name, contact, birthdate, gender, type, caregiver);
		}
	}
}
