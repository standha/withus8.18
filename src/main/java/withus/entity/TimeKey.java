package withus.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class TimeKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Column(name="id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name="date")
    private LocalDate date;

    @EqualsAndHashCode.Include
    @Column(name="time")
    private LocalTime time;

    public TimeKey(){}

    public TimeKey(String id, LocalDate date, LocalTime time) {
        super();
        this.id = id;
        this.date = date;
        this.time = time;
    }
    public String getId(){
        return id;
    }
    public LocalDate getDate(){
        return date;
    }
    public LocalTime getTime() { return time; }
}
