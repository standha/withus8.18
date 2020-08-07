package com.bluecore.withus.entity.alarms;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluecore.withus.entity.User;
import com.bluecore.withus.util.Utility;

@Entity
@Table(indexes = @Index(columnList = "date"))
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
	private LocalDate date;

	@Column
	private LocalTime time;

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
	private Appointment(User user, boolean enabled, LocalDate date, LocalTime time) {
		this.user = user;
		this.enabled = enabled;
		this.date = date;
		this.time = time;
	}

	public long getId() { return id; }
	public User getUser() { return user; }
	public boolean isEnabled() { return enabled; }
	public LocalDate getDate() { return date; }
	public LocalTime getTime() { return time; }

	public void setUser(User user) { this.user = user; }

	public String getDateString() { return Utility.DATE_FORMATTER.format(date); }
	public String getTimeString() { return Utility.TIME_FORMATTER.format(time); }

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private User user;
		private boolean enabled;
		private LocalDate date;
		private LocalTime time;

		public Builder setUser(User user) {
			this.user = user;
			return this;
		}
		public Builder setEnabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}
		public Builder setDate(LocalDate date) {
			this.date = date;
			return this;
		}
		public Builder setTime(LocalTime time) {
			this.time = time;
			return this;
		}

		public Appointment createAppointment() {
			return new Appointment(user, enabled, date, time);
		}
	}
}
