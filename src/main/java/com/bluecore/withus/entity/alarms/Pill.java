package com.bluecore.withus.entity.alarms;

import java.time.LocalTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.bluecore.withus.configuration.JsonIgnore;
import com.bluecore.withus.entity.User;

@Entity
public class Pill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(columnDefinition = "BIT(1)")
	private boolean enabled;

	@Column
	@JsonIgnore
	private LocalTime breakfast;

	@Column
	@JsonIgnore
	private LocalTime lunch;

	@Column
	@JsonIgnore
	private LocalTime dinner;

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		Pill pill = (Pill)o;
		return id == pill.id;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Pill() { }
	private Pill(User user, boolean enabled, LocalTime breakfast, LocalTime lunch, LocalTime dinner) {
		this.user = user;
		this.enabled = enabled;
		this.breakfast = breakfast;
		this.lunch = lunch;
		this.dinner = dinner;
	}

	public long getId() { return id; }
	public User getUser() { return user; }
	public boolean isEnabled() { return enabled; }
	public LocalTime getBreakfast() { return breakfast; }
	public LocalTime getLunch() { return lunch; }
	public LocalTime getDinner() { return dinner; }

	public void setUser(User user) { this.user = user; }

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private User user;
		private boolean enabled;
		private LocalTime breakfast;
		private LocalTime lunch;
		private LocalTime dinner;

		public Builder setUser(User user) {
			this.user = user;
			return this;
		}
		public Builder setEnabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}
		public Builder setBreakfast(LocalTime breakfast) {
			this.breakfast = breakfast;
			return this;
		}
		public Builder setLunch(LocalTime lunch) {
			this.lunch = lunch;
			return this;
		}
		public Builder setDinner(LocalTime dinner) {
			this.dinner = dinner;
			return this;
		}
		public Pill createPill() {
			return new Pill(user, enabled, breakfast, lunch, dinner);
		}
	}
}
