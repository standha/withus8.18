package withus.entity.alarm;

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

import withus.configuration.JsonIgnore;
import withus.entity.User;
import withus.util.Utility;

@Entity
@Table(
	indexes = {
		@Index(columnList = "date"),
		@Index(columnList = "enabled,date"),
		@Index(columnList = "enabled,date,time")
	}
)
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(columnDefinition = "BIT(1)")
	private boolean enabled;

	@Column(columnDefinition = "DATE")
	@JsonIgnore
	private LocalDate date;

	@Column(columnDefinition = "TIME")
	@JsonIgnore
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
	public Appointment(User user, boolean enabled, LocalDate date, LocalTime time) {
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

	public String getDateString() { return Utility.format(date); }
	public String getTimeString() { return Utility.format(time); }
}
