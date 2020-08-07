package com.bluecore.withus.entity.alarms;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bluecore.withus.entity.User;

@Entity
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(columnDefinition = "BIT(1)")
	private boolean enabled;

	@Column
	private LocalDateTime dateTime;

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		Appointment pill = (Appointment)o;
		return id == pill.id;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Appointment() { }
	private Appointment(User user, boolean enabled, LocalDateTime dateTime) {
		this.user = user;
		this.enabled = enabled;
		this.dateTime = dateTime;
	}

	public long getId() { return id; }
	public User getUser() { return user; }
	public boolean isEnabled() { return enabled; }
	public LocalDateTime getDateTime() { return dateTime; }

	public void setUser(User user) { this.user = user; }

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private User user;
		private boolean enabled;
		private LocalDateTime dateTime;
		public Builder setUser(User user) {
			this.user = user;
			return this;
		}
		public Builder setEnabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}
		public Builder setDateTime(LocalDateTime dateTime) {
			this.dateTime = dateTime;
			return this;
		}
		public Appointment createAppointment() {
			return new Appointment(user, enabled, dateTime);
		}
	}
}
