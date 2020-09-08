package withus.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class RecordKey implements Serializable {

    private static final long serialVersionUID = 1L;
    @EqualsAndHashCode.Include
    @Column(name="patient_id")
    private String id;

    @EqualsAndHashCode.Include
    @Column(name="registration_date")
    private LocalDate date;

    public RecordKey(){}

    public RecordKey(String id, LocalDate date) {
        super();
        this.id = id;
        this.date = date;
    }
    public String getId(){
        return id;
    }
    public LocalDate getDate(){
        return date;
    }
}