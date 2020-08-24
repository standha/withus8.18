package withus.entity.alarm;

import java.time.LocalDate;
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
import withus.util.Utility;

@Entity
@Table(indexes = @Index(columnList = "date,finished"))
public class PillHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "pill_id")
	private Pill pill;

	@Column(columnDefinition = "DATE")
	@JsonIgnore
	private LocalDate date;

	@Column(columnDefinition = "BIT(1)")
	private boolean finished;

	public PillHistory() { }
	private PillHistory(Pill pill, LocalDate date, boolean finished) {
		this.pill = pill;
		this.date = date;
		this.finished = finished;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		PillHistory that = (PillHistory)o;
		return id == that.id;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public long getId() { return id; }
	public Pill getPill() { return pill; }
	public LocalDate getDate() { return date; }
	public boolean isFinished() { return finished; }

	public void setFinished(boolean finished) { this.finished = finished; }

	public String getDateString() { return Utility.format(date); }

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Pill pill;
		private LocalDate date;
		private boolean finished;

		public Builder setPill(Pill pill) {
			this.pill = pill;
			return this;
		}
		public Builder setDate(LocalDate date) {
			this.date = date;
			return this;
		}
		public Builder setFinished(boolean finished) {
			this.finished = finished;
			return this;
		}
		public PillHistory create() {
			return new PillHistory(pill, date, finished);
		}
	}
}
