package com.bluecore.withus.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Entity
@Table(indexes = @Index(columnList = "id,password"))
public class User implements Serializable {
	@Id
	@Column(columnDefinition = "VARCHAR(128) NOT NULL", length = 128)
	@NonNull
	protected String id;

	@Column(columnDefinition = "VARCHAR(255)")
	protected String password;

	@Column(columnDefinition = "VARCHAR(32)", length = 32)
	protected String name;

	@Column(columnDefinition = "VARCHAR(32) NOT NULL", length = 32, unique = true)
	@NonNull
	private String contact;

	@Column(columnDefinition = "DATE")
	@Nullable
	private LocalDate birthdate;

	@Column(columnDefinition = "VARCHAR(6)", length = 6)
	@Enumerated(EnumType.STRING)
	@Nullable
	private Sex sex;

	@OneToOne
	@JoinColumn(name = "caregiver_contact", columnDefinition = "VARCHAR(32)", referencedColumnName = "contact")
	@Nullable
	private User caregiver;

	public User() { }
	private User(@NonNull String id, String password, String name, @NonNull String contact, @Nullable LocalDate birthdate, @Nullable Sex sex, @Nullable User caregiver) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.contact = contact;
		this.birthdate = birthdate;
		this.sex = sex;
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

	@NonNull
	public String getId() { return id; }
	public String getPassword() { return password; }
	public String getName() { return name; }
	@NonNull
	public String getContact() { return contact; }
	@Nullable
	public LocalDate getBirthdate() { return birthdate; }
	@Nullable
	public Sex getSex() { return sex; }
	@Nullable
	public User getCaregiver() { return caregiver; }

	public enum Sex {
		MALE, FEMALE
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String id;
		private String password;
		private String name;
		private String contact;
		private LocalDate birthdate;
		private Sex sex;
		private User caregiver;

		public Builder setId(String id) {
			this.id = id;
			return this;
		}
		public Builder setPassword(String password) {
			this.password = password;
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
		public Builder setSex(Sex sex) {
			this.sex = sex;
			return this;
		}
		public Builder setCaregiver(User caregiver) {
			this.caregiver = caregiver;
			return this;
		}

		public User createUser() {
			return new User(id, password, name, contact, birthdate, sex, caregiver);
		}
	}
}
