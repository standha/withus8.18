package withus.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class WeekUserKey implements Serializable {
    private static final long serialVersionUID = 1L;
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name = "week")
    private Integer week;
    public WeekUserKey() {

    }

    public WeekUserKey(String id, Integer week) {
        super();
        this.id = id;
        this.week = week;
    }

    public String getId() {
        return id;
    }

    public Integer getWeek() {
        return week;
    }
}
