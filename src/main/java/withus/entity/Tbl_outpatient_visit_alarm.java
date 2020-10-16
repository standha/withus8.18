package withus.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import withus.configuration.JsonIgnore;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter
@Getter
@Table(name = "tbl_outpatient_visit_alarm")
public class Tbl_outpatient_visit_alarm {
    @Id
    @Column(name = "outpaient_visit_id")
    private String id;

    @Column(name = "visit_Date")
    @JsonIgnore
    private LocalDate outPatientVisitDate;

    @Column(name = "visit_Time")
    @JsonIgnore
    private LocalTime outPatientVisitTime;

    @Column(name = "visit_Alarm")
    @ColumnDefault("0")
    private Boolean visitAlarm;
}
